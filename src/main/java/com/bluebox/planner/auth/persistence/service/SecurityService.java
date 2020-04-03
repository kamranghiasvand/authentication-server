package com.bluebox.planner.auth.persistence.service;

import com.bluebox.planner.auth.common.exception.UnAuthorizationException;
import com.bluebox.planner.auth.security.JwtTokenUtil;
import com.bluebox.planner.auth.security.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author by kamran ghiasvand
 */
@Service
public class SecurityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegularUserService.class);
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public SecurityService(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public String login(String username, String password) throws UnAuthorizationException {
        UserDetails user = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        authenticationManager.authenticate(authentication);
        if (authentication.isAuthenticated()) {
            return jwtTokenUtil.generateToken(user);
        }
        throw new UnAuthorizationException(MessageFormat.format("{0} is not authorized",username));
    }

    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }
        return null;
    }
}
