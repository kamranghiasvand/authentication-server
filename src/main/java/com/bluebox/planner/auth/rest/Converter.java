package com.bluebox.planner.auth.rest;


import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.viewModel.BaseDto;
import com.bluebox.planner.auth.persistence.entity.BaseDomainEntity;

import java.io.Serializable;

public interface Converter<E extends BaseDomainEntity<I>, D extends BaseDto<I> ,I extends Serializable> {
    E convert(D dto, Object... args ) throws GlobalException;
    D convert(E entity, Object... args) throws GlobalException;
}
