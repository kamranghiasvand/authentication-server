package com.bluebox.planner.auth.persistence.repository;

import com.bluebox.planner.auth.persistence.entity.PhoneVerificationEntity;

import java.util.Optional;

/**
 * @author by kamran ghiasvand
 */
public interface PhoneVerificationRepository extends BaseRepository<PhoneVerificationEntity, Long> {
    Optional<PhoneVerificationEntity> findFirstByPhoneNumberAndCode(String phone,String code);
}
