package com.bluebox.security.authenticationserver.persistence.aspect;

import com.bluebox.security.authenticationserver.security.UserPrincipal;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.bluebox.security.authenticationserver.common.Constants.UNKNOWN_DOMAIN;

/**
 * @author Kamran Ghiasvand
 */

@Aspect
@Component
public class DomainFilterAdvisor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainFilterAdvisor.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Pointcut("execution(public * com.bluebox.security.authenticationserver.persistence.repository.DomainAwareRepository+.*(..))")
    protected void domainAwareRepositoryMethod() {

    }

    @Around(value = "domainAwareRepositoryMethod()")
    public Object enableOwnerFilter(ProceedingJoinPoint joinPoint) throws Throwable {
        Session session = null;
        try {
            String domain = getDomainFromPrincipal();
            session = entityManager.unwrap(Session.class);
            session.enableFilter("domainFilter").setParameter("domainParam", domain);

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        Object obj = joinPoint.proceed();
        if (session != null)
            session.disableFilter("domainFilter");
        return obj;
    }

    private String getDomainFromPrincipal() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
                return UNKNOWN_DOMAIN;
            final var principal = (UserPrincipal) authentication.getPrincipal();
            return principal.getDomain() != null ? principal.getDomain() : "";
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return UNKNOWN_DOMAIN;
    }
}
