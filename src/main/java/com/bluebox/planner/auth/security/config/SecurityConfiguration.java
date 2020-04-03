package com.bluebox.planner.auth.security.config;

import com.bluebox.planner.auth.common.PathConstant;
import com.bluebox.planner.auth.persistence.entity.PermissionEntity;
import com.bluebox.planner.auth.persistence.repository.PermissionRepository;
import com.bluebox.planner.auth.security.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

import static com.bluebox.planner.auth.common.PathConstant.LOGIN_BASE;
import static com.bluebox.planner.auth.common.PathConstant.REGISTRATION_BASE;

/**
 * @author by kamran ghiasvand
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);
    private final PermissionRepository permissionRepository;
    private final UserDetailsServiceImpl userDetailsService;
//    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
//    private final JwtRequestFilter jwtRequestFilter;
    private List<String> commonUrl = new ArrayList<>();

    {
        commonUrl.add(REGISTRATION_BASE);
        commonUrl.add(LOGIN_BASE);
        commonUrl.add("/**/*swagger*/**");
        commonUrl.add("/api-docs*");
    }

    @Autowired
    public SecurityConfiguration(PermissionRepository permissionRepository,
                                 UserDetailsServiceImpl userDetailsService
//            ,
//                                 CustomAuthenticationEntryPoint customAuthenticationEntryPoint
//            ,
//                                 JwtRequestFilter jwtRequestFilter
    ) {
        super();
        this.permissionRepository = permissionRepository;
        this.userDetailsService = userDetailsService;
//        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
//        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.logout().clearAuthentication(true).invalidateHttpSession(true).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        List<PermissionEntity> all = permissionRepository.findAll();
        for (PermissionEntity permission : all) {
            http.authorizeRequests()
                    .antMatchers(permission.getMethod(), permission.getUrl())
                    .hasAnyAuthority(permission.getName());
        }
        http.authorizeRequests().anyRequest().authenticated();
//        handleExceptions(http);
    }

//    private void handleExceptions(HttpSecurity http) throws Exception {
//        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        for (String url : commonUrl) {
            web.ignoring().antMatchers(url);
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(9);
    }

}
