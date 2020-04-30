package com.bluebox.security.authenticationserver.persistence.service.base;

import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.persistence.entity.BaseEntity;

import java.io.Serializable;

/**
 * @author kamran ghiasvand
 */
public interface CommandService<E extends BaseEntity<I>, I extends Serializable> {

    E create(E entity) throws GlobalException;

    E update(E entity) throws GlobalException;

    E remove(I id) throws GlobalException;
}
