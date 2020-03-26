package com.bluebox.planner.auth.persistence.service.base;

import com.bluebox.planner.auth.common.dto.BaseDto;
import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.persistence.entity.BaseEntity;

import java.io.Serializable;

/**
 * @author kamran ghiasvand
 */
public interface CommandService<E extends BaseEntity<I>, D extends BaseDto<I>, I extends Serializable> {

    E create(D t) throws GlobalException;

    E update(D t) throws GlobalException;
}
