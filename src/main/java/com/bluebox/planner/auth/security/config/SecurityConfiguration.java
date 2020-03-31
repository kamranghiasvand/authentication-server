package com.bluebox.planner.auth.security.config;

import com.bluebox.planner.auth.persistence.entity.PermissionEntity;
import com.bluebox.planner.auth.persistence.repository.PermissionRepository;
import com.bluebox.planner.auth.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by kamran ghiasvand
 */
@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);
    private final PermissionRepository permissionRepository;
    private final UserService userDetailsService;
    private List<String> commonUrl = new ArrayList<>();

    {
        commonUrl.add("/api/registration");
        commonUrl.add("/**/*swagger*/**");
        commonUrl.add("/api-docs*");
        commonUrl.add("/auth/v1/swagger-ui.html");
    }

    @Autowired
    public SecurityConfiguration(PermissionRepository permissionRepository, UserService userDetailsService) {
        super();
        this.permissionRepository = permissionRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().and()
                .anonymous().disable();
        List<PermissionEntity> all = permissionRepository.findAll();
        for (PermissionEntity permission : all) {
            http.authorizeRequests()
                    .antMatchers(permission.getMethod(), permission.getUrl())
                    .hasAuthority(permission.getName());
        }
        handleExceptions(http);
    }

    private void handleExceptions(HttpSecurity http) throws Exception {
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        for (String url : commonUrl) {
            web.ignoring().antMatchers(url);
        }
    }

    @Override
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

}
