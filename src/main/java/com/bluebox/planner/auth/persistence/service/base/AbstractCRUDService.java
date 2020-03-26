package com.bluebox.planner.auth.persistence.service.base;

import com.bluebox.planner.auth.common.dto.BaseDto;
import com.bluebox.planner.auth.common.dto.SortField;
import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.util.ConvertUtil;
import com.bluebox.planner.auth.persistence.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;

@Transactional(readOnly = true)
public abstract class AbstractCRUDService<E extends BaseEntity<I>, D extends BaseDto<I>,
        F extends SortField, I extends Serializable>
        extends AbstractQueryService<E, D, F, I>
        implements CommandService<E, D,I> {


    @Transactional(rollbackFor = GlobalException.class)
    @Override
    public E create(D dto) throws GlobalException {
        E entity = ConvertUtil.to(dto, getEntityClass());
        return getRepository().save(entity);
    }

    @Transactional(rollbackFor = GlobalException.class)
    @Override
    public E update(D dto) throws GlobalException {
        E foundedInDB = get(dto.getId(), this::extract);
        edit(foundedInDB, dto);
        return getRepository().save(foundedInDB);
    }

    protected abstract Class<E> getEntityClass();

    protected abstract void edit(E foundedInDB, D dto) throws GlobalException;

}
