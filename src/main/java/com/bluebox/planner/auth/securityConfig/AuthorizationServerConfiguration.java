//package com.bluebox.planner.auth.securityConfig;
//
//import com.bluebox.planner.auth.persistence.service.ClientService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.securityConfig.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.securityConfig.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.securityConfig.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.securityConfig.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.securityConfig.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
//import java.security.KeyPair;
//
///**
// * @author by kamran ghiasvand
// */
//@EnableAuthorizationServer
//@Configuration
//public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
//    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationServerConfiguration.class);
//    private final AuthenticationManager authenticationManager;
//    private final UserDetailsService userDetailsService;
//    private final ClientService clientDetailsService;
//    @Value("${security.oauth2.authorizationserver.jwt.enabled:true}")
//    private boolean jwtEnabled;
//    private KeyPair keyPair;
//
//    @Autowired
//    public AuthorizationServerConfiguration(AuthenticationConfiguration authenticationConfiguration,
//                                            UserDetailsService userDetailsService,
//                                            ClientService clientDetailsService,
//                                            KeyPair keyPair) throws Exception {
//        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
//        this.userDetailsService = userDetailsService;
//        this.clientDetailsService = clientDetailsService;
//        this.keyPair = keyPair;
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        // @formatter:off
//        clients.inMemory()
//                .withClient("reader")
//                    .authorizedGrantTypes("password")
//                    .secret("{noop}secret")
//                    .scopes("message:read")
//                    .accessTokenValiditySeconds(600_000_000)
//                .and()
//                .withClient("writer")
//                    .authorizedGrantTypes("password")
//                    .secret("{noop}secret")
//                    .scopes("message:write")
//                    .accessTokenValiditySeconds(600_000_000)
//                .and()
//                .withClient("noscopes")
//                    .authorizedGrantTypes("password")
//                    .secret("{noop}secret")
//                    .scopes("none")
//                    .accessTokenValiditySeconds(600_000_000);
//        // @formatter:on
//    }
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//        // @formatter:off
//        endpoints
//                .authenticationManager(this.authenticationManager)
//                .tokenStore(tokenStore());
//
//        if (this.jwtEnabled) {
//            endpoints
//                    .accessTokenConverter(accessTokenConverter());
//        }
//        // @formatter:on
//    }
//    @Bean
//    public TokenStore tokenStore() {
//        if (this.jwtEnabled) {
//            return new JwtTokenStore(accessTokenConverter());
//        } else {
//            return new InMemoryTokenStore();
//        }
//    }
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setKeyPair(this.keyPair);
//
//        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
//        accessTokenConverter.setUserTokenConverter(new SubjectAttributeUserTokenConverter());
//        converter.setAccessTokenConverter(accessTokenConverter);
//
//        return converter;
//    }
//}
