package com.bluebox.planner.auth.persistence.service.base;

import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.persistence.entity.BaseEntity;

import java.io.Serializable;

/**
 * @author kamran ghiasvand
 */
public interface CommandService<E extends BaseEntity<I>, I extends Serializable> {

    E create(E entity) throws GlobalException;

    E update(E entity) throws GlobalException;
}
