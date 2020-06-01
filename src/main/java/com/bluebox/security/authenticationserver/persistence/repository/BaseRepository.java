package com.bluebox.security.authenticationserver.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Yaser(amin) Sadeghi
 */
@NoRepositoryBean
@Transactional
public interface BaseRepository<T, I> extends JpaSpecificationExecutor<T>, JpaRepository<T, I> {
}
