package com.bluebox.security.authenticationserver.persistence.repository;


import com.bluebox.security.authenticationserver.persistence.entity.regular.RegularUserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author by kamran ghiasvand
 */
public interface RegularUserRepository extends DomainAwareRepository<RegularUserEntity, Long> {
    Optional<RegularUserEntity> findByPhone(String phone);
}
