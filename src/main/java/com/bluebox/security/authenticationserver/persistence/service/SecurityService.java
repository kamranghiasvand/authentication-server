package com.bluebox.security.authenticationserver.persistence.service;//package com.bluebox.security.authenticationserver.persistence.service;
//
//import com.bluebox.security.authenticationserver.common.exception.UnAuthorizationException;
//import com.bluebox.security.authenticationserver.security.JwtTokenUtil;
//import com.bluebox.security.authenticationserver.security.service.UserDetailsServiceImpl;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.text.MessageFormat;
//import java.util.UUID;
//
///**
// * @author by kamran ghiasvand
// */
//@Service
//public class SecurityService {
//    private static final Logger LOGGER = LoggerFactory.getLogger(RegularUserService.class);
//    private final AuthenticationManager authenticationManager;
//    private final UserDetailsServiceImpl userDetailsService;
//    private final JwtTokenUtil jwtTokenUtil;
//    private final CustomAuthenticationProvider provider;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public SecurityService(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtTokenUtil jwtTokenUtil, CustomAuthenticationProvider provider, PasswordEncoder passwordEncoder) {
//        this.authenticationManager = authenticationManager;
//        this.userDetailsService = userDetailsService;
//        this.jwtTokenUtil = jwtTokenUtil;
//        this.provider = provider;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    public String login(String username, String password) throws UnAuthorizationException {
//        UserDetails user = userDetailsService.loadUserByUsername(username);
//        if(user.getPassword().equals(passwordEncoder.encode(password)))
//            provider.addToken(username, UUID.randomUUID().toString());
//        throw new UnAuthorizationException(MessageFormat.format("{0} is not authorized",username));
//    }
//
//    public String findLoggedInUsername() {
//        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
//        if (userDetails instanceof UserDetails) {
//            return ((UserDetails) userDetails).getUsername();
//        }
//        return null;
//    }
//}
