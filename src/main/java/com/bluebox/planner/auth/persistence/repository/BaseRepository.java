package com.bluebox.planner.auth.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;
import java.util.List;

/**
 * @author Yaser(amin) Sadeghi
 */
@NoRepositoryBean
public interface BaseRepository<T, I> extends JpaSpecificationExecutor<T>, JpaRepository<T, I> {
    List<T> findByIdIn(Collection<I> ids);
}
