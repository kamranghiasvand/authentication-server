package com.bluebox.security.authenticationserver.persistence.aspect;

import com.bluebox.security.authenticationserver.security.UserPrincipal;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
            UserPrincipal user = getUserPrinciple();
            session = entityManager.unwrap(Session.class);
            Filter filter = session.enableFilter("domainFilter").setParameter("domainParam", user.getDomain());

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        Object obj = joinPoint.proceed();
        if (session != null)
            session.disableFilter("domainFilter");
        return obj;

    }

    private UserPrincipal getUserPrinciple() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            throw new AuthenticationServiceException("no authenticated user found");
        return (UserPrincipal) authentication.getPrincipal();

    }
}
