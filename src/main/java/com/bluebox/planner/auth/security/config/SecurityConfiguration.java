package com.bluebox.planner.auth.security.config;

import com.bluebox.planner.auth.persistence.entity.PermissionEntity;
import com.bluebox.planner.auth.persistence.repository.PermissionRepository;
import com.bluebox.planner.auth.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author by kamran ghiasvand
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final PermissionRepository permissionRepository;
    private final UserService userDetailsService;

    @Autowired
    public SecurityConfiguration(PermissionRepository permissionRepository, UserService userDetailsService) {
        super();
        this.permissionRepository = permissionRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        List<PermissionEntity> all = permissionRepository.findAll();
        for (PermissionEntity permission : all) {
            http.authorizeRequests()
                    .antMatchers(permission.getMethod(),permission.getUrl())
                    .hasAuthority(permission.getName());
        }
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }
}
