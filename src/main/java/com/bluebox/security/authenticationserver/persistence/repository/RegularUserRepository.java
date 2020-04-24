package com.bluebox.security.authenticationserver.persistence.repository;


import com.bluebox.security.authenticationserver.persistence.entity.regular.RegularUserEntity;

import java.util.Optional;

/**
 * @author by kamran ghiasvand
 */
public interface RegularUserRepository extends BaseRepository<RegularUserEntity, Long> {
    Optional<RegularUserEntity> findByEmail(String username);
}
