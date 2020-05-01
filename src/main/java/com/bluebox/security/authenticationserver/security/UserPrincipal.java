package com.bluebox.security.authenticationserver.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author by kamran ghiasvand
 */
public class UserPrincipal implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private Set<GrantedAuthority> roles = new HashSet<>();
    private String domain;

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getDomain() {
        return domain;
    }

    public Long getId() {
        return id;
    }

    public static class Builder {
        private UserPrincipal instance = new UserPrincipal();

        public Builder setId(Long id) {
            instance.id = id;
            return this;
        }

        public Builder setEnabled(boolean enabled) {
            instance.enabled = enabled;
            return this;
        }

        public Builder setDomain(String domain) {
            instance.domain = domain;
            return this;
        }

        public Builder setUsername(String username) {
            instance.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            instance.password = password;
            return this;
        }

        public Builder addAuthority(String role) {
            instance.roles.add(new SimpleGrantedAuthority(role));
            return this;
        }

        public UserPrincipal build() {
            return instance;
        }
    }
}
