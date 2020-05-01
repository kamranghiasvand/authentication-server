package com.bluebox.security.authenticationserver.security.service;

import com.bluebox.security.authenticationserver.persistence.entity.PermissionEntity;
import com.bluebox.security.authenticationserver.persistence.entity.administrator.AdminUserEntity;
import com.bluebox.security.authenticationserver.persistence.entity.administrator.AdminUserPermissionEntity;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RegularUserEntity;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RoleEntity;
import com.bluebox.security.authenticationserver.persistence.repository.AdminUserRepository;
import com.bluebox.security.authenticationserver.persistence.repository.RegularUserRepository;
import com.bluebox.security.authenticationserver.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Optional;

/**
 * @author by kamran ghiasvand
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final RegularUserRepository regularUserRepository;
    private final AdminUserRepository adminUserRepository;

    @Autowired
    public UserDetailsServiceImpl(RegularUserRepository regularUserRepository, AdminUserRepository adminUserRepository) {

        this.regularUserRepository = regularUserRepository;
        this.adminUserRepository = adminUserRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("trying to find user with username '{}'", username);
        UserDetails userDetails = tryToFindAdminUser(username);
        if (userDetails != null)
            return userDetails;
        userDetails = tryToFindRegularUser(username);
        if (userDetails != null)
            return userDetails;
        throw new UsernameNotFoundException(MessageFormat.format("user with username '{}' with not found", username));
    }

    private UserDetails tryToFindRegularUser(String username) {
        LOGGER.debug("finding user with phone: '{}' among regulars", username);
        var resp = regularUserRepository.findByPhone(username);
        if (resp.isEmpty()) {
            LOGGER.debug("user with phone {} not found among regulars", username);
            return null;
        }
        RegularUserEntity user = resp.get();
        LOGGER.debug("user with {} has found {}", username, user);
        LOGGER.debug("building a principle based on user with id: {}", user.getId());
        UserPrincipal.Builder builder = UserPrincipal.newBuilder()
                .setId(user.getId())
                .setDomain(user.getDomain())
                .setEnabled(user.isEnabled())
                .setPassword(user.getPassword())
                .setUsername(username);
        if (!CollectionUtils.isEmpty(user.getRoles()))
            for (RoleEntity role : user.getRoles()) {
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    for (PermissionEntity permission : role.getPermissions()) {
                        builder = builder.addAuthority(permission.getName());
                    }
            }
        final var principal = builder.build();
        LOGGER.debug("principle is: {}", principal);
        return principal;
    }

    private UserDetails tryToFindAdminUser(String username) {
        LOGGER.debug("finding admin with email: '{}' among admins", username);
        var resp = adminUserRepository.findByEmail(username);
        if (resp.isEmpty()) {
            LOGGER.debug("admin with email {} not found among admin", username);
            return null;
        }
        AdminUserEntity user = resp.get();
        LOGGER.debug("admin with {} has found {}", username, user);
        LOGGER.debug("building a principle based on admin with id: {}", user.getId());
        UserPrincipal.Builder builder = UserPrincipal.newBuilder()
                .setId(user.getId())
                .setDomain(user.getDomain())
                .setEnabled(user.isEnabled())
                .setUsername(username)
                .setPassword(user.getPassword());
        for (AdminUserPermissionEntity permission : user.getPermissions())
            builder = builder.addAuthority(permission.getPermission().getName());
        final var principal = builder.build();
        LOGGER.debug("principle is: {}", principal);
        return principal;
    }
}
