package com.bluebox.planner.auth.persistence.service.base;


import com.bluebox.planner.auth.common.dto.BaseDto;
import com.bluebox.planner.auth.common.dto.PaginatedResultDTO;
import com.bluebox.planner.auth.common.dto.SortField;
import com.bluebox.planner.auth.common.dto.SortableDtoPage;
import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.persistence.entity.BaseEntity;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Yaser(amin) Sadeghi
 */
public interface QueryService<E extends BaseEntity<I>, D extends BaseDto<I>,
        F extends SortField, I extends Serializable> {

    D fetch(I key) throws GlobalException;

    <K> E get(K key, Function<K, Optional<E>> extractor) throws GlobalException;

    PaginatedResultDTO<E> search(SortableDtoPage<D, F,I> dto);

}
