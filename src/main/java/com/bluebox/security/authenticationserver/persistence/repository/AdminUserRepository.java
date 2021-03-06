package com.bluebox.security.authenticationserver.persistence.repository;


import com.bluebox.security.authenticationserver.persistence.entity.administrator.AdminUserEntity;

import java.util.Optional;

/**
 * @author by kamran ghiasvand
 */
public interface AdminUserRepository extends DomainAwareRepository<AdminUserEntity, Long> {

    Optional<AdminUserEntity> findByEmail(String email);
}
