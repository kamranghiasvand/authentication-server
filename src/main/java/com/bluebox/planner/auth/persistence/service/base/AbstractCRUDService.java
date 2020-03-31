package com.bluebox.planner.auth.persistence.service.base;

import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.viewModel.SortField;
import com.bluebox.planner.auth.common.viewModel.BaseCto;
import com.bluebox.planner.auth.persistence.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Transactional(readOnly = true)
public abstract class AbstractCRUDService<
        E extends BaseEntity<I>,
        C extends BaseCto,
        F extends SortField,
        I extends Serializable>
        extends AbstractQueryService<E, C, F, I>
        implements CommandService<E,I> {


    @Transactional
    @Override
    public E create(E entity){
        return getRepository().save(entity);
    }

    @Transactional(rollbackFor = GlobalException.class)
    @Override
    public E update(E entity) throws GlobalException {
        E foundedInDB = fetch(entity.getId());
        edit(foundedInDB, entity);
        return getRepository().save(foundedInDB);
    }

    protected abstract Class<E> getEntityClass();

    protected abstract void edit(E foundedInDB, E newEntity) throws GlobalException;

}
