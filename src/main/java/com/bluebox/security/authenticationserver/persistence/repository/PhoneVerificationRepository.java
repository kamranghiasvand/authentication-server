package com.bluebox.security.authenticationserver.persistence.repository;

import com.bluebox.security.authenticationserver.persistence.entity.PhoneVerificationEntity;

import java.util.Optional;

/**
 * @author by kamran ghiasvand
 */
public interface PhoneVerificationRepository extends BaseRepository<PhoneVerificationEntity, Long> {
    Optional<PhoneVerificationEntity> findFirstByPhoneNumberAndCode(String phone, String code);

    Optional<PhoneVerificationEntity> findFirstByPhoneNumber(String phone);
}
