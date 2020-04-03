package com.bluebox.planner.auth.security.service;

import com.bluebox.planner.auth.persistence.entity.PermissionEntity;
import com.bluebox.planner.auth.persistence.entity.administrator.AdminUserEntity;
import com.bluebox.planner.auth.persistence.entity.administrator.AdminUserPermissionEntity;
import com.bluebox.planner.auth.persistence.entity.regular.RegularRoleEntity;
import com.bluebox.planner.auth.persistence.entity.regular.RegularUserEntity;
import com.bluebox.planner.auth.persistence.repository.AdminUserRepository;
import com.bluebox.planner.auth.persistence.repository.RegularUserRepository;
import com.bluebox.planner.auth.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
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
        UserDetails userDetails = tryToFindAdminUser(username);
        if (userDetails != null)
            return userDetails;
        userDetails = tryToFindRegularUser(username);
        if (userDetails != null)
            return userDetails;
        throw new UsernameNotFoundException(MessageFormat.format("username '{}' with not found", username));
    }

    private UserDetails tryToFindRegularUser(String username) {
        LOGGER.info("finding user: '{}' among regulars", username);
        Optional<RegularUserEntity> byEmail = regularUserRepository.findByEmail(username);
        if (byEmail.isEmpty())
            return null;
        RegularUserEntity user = byEmail.get();
        UserPrincipal.Builder builder = UserPrincipal.newBuilder()
                .setEnabled(user.isEnabled())
                .setPassword(user.getPassword())
                .setUsername(username);
        for (RegularRoleEntity role : user.getRoles()) {
            for (PermissionEntity permission : role.getPermissions()) {
                builder.addAuthority(permission.getName());
            }
        }
        return builder.build();
    }

    private UserDetails tryToFindAdminUser(String username) {
        LOGGER.info("finding user: '{}' among admins", username);
        Optional<AdminUserEntity> byEmail = adminUserRepository.findByEmail(username);
        if (byEmail.isEmpty())
            return null;
        AdminUserEntity user = byEmail.get();
        UserPrincipal.Builder builder = UserPrincipal.newBuilder()
                .setEnabled(user.isEnabled())
                .setUsername(username)
                .setPassword(user.getPassword());
        for (AdminUserPermissionEntity permission : user.getPermissions())
            builder.addAuthority(permission.getPermission().getName());
        return builder.build();
    }
}
