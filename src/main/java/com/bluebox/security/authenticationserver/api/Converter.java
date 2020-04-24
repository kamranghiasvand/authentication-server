package com.bluebox.security.authenticationserver.api;


import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.common.viewModel.BaseDto;
import com.bluebox.security.authenticationserver.persistence.entity.BaseDomainEntity;

import java.io.Serializable;

public interface Converter<E extends BaseDomainEntity<I>, D extends BaseDto<I> ,I extends Serializable> {
    E convert(D dto, Object... args) throws GlobalException;
    D convert(E entity, Object... args) throws GlobalException;
}
