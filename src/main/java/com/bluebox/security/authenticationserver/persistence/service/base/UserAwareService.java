package com.bluebox.security.authenticationserver.persistence.service.base;

import com.bluebox.security.authenticationserver.common.viewModel.BaseCto;
import com.bluebox.security.authenticationserver.common.viewModel.SortField;
import com.bluebox.security.authenticationserver.persistence.entity.BaseEntity;
import com.bluebox.security.authenticationserver.security.UserPrincipal;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.Serializable;

/**
 * @author Kamran Ghiasvand
 */
public abstract class UserAwareService<E extends BaseEntity<I>, C extends BaseCto, F extends SortField, I extends Serializable> extends AbstractCRUDService<E, C, F, I> {

    private final UserDetailsService userDetailsService;

    protected UserAwareService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    public UserPrincipal getUserPrinciple() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            return null;
        return (UserPrincipal) authentication.getPrincipal();

    }
}
