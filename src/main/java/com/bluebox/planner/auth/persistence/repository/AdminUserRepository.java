package com.bluebox.planner.auth.persistence.repository;

import com.bluebox.planner.auth.persistence.entity.administrator.AdminUserEntity;
import com.bluebox.planner.auth.persistence.entity.regular.RegularUserEntity;

import java.util.Optional;

/**
 * @author by kamran ghiasvand
 */
public interface AdminUserRepository extends BaseRepository<AdminUserEntity,Long> {

    Optional<AdminUserEntity> findByEmail(String username);
}
