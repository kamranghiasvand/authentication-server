package com.bluebox.security.authenticationserver.persistence.repository;

import com.bluebox.security.authenticationserver.persistence.entity.BaseDomainEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author Yaser(amin) Sadeghi
 */
@NoRepositoryBean
public interface DomainAwareRepository<T extends BaseDomainEntity<I>, I extends Serializable> extends BaseRepository<T, I> {

}
