//package com.bluebox.planner.auth.security.config;
//
//import com.bluebox.planner.auth.security.service.UserDetailsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
//
///**
// * @author by kamran ghiasvand
// */
//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//
//
//    private static final String SCOPE_READ = "read";
//    private static final String SCOPE_WRITE = "write";
//
//    private final UserDetailsService userDetailsService;
//    private final AuthenticationManager authenticationManager;
//
//
//    @Autowired
//    public AuthorizationServerConfig(
//            UserDetailsServiceImpl userDetailsService, AuthenticationManager authenticationManager) throws Exception {
//
//        this.userDetailsService = userDetailsService;
//        this.authenticationManager = authenticationManager;
//    }
//
//
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new InMemoryTokenStore();
//
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//
//        endpoints
//                .authenticationManager(authenticationManager)
//                .userDetailsService(userDetailsService)
//                .tokenStore(tokenStore());
//                //.accessTokenConverter(accessTokenConverter());
//    }
//}