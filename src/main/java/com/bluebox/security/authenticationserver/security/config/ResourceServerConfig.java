package com.bluebox.security.authenticationserver.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import static com.bluebox.security.authenticationserver.common.PathConstant.PERMIT_ALL;
import static com.bluebox.security.authenticationserver.common.PathConstant.REGISTRATION_BASE;

/**
 * @author by kamran ghiasvand
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        final var requests = http
                .authorizeRequests();
        for (String url : PERMIT_ALL) {
            requests.antMatchers(url).permitAll();
        }

    }
}