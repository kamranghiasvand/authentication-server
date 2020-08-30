package com.bluebox.security.authenticationserver.persistence.service.base;

import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.common.exception.ResourceNotFoundException;
import com.bluebox.security.authenticationserver.common.viewModel.BaseCto;
import com.bluebox.security.authenticationserver.common.viewModel.SortField;
import com.bluebox.security.authenticationserver.persistence.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

import static com.bluebox.security.authenticationserver.common.Constants.VALIDATION_IS_NULL_MSG;
import static java.text.MessageFormat.format;

@Transactional
public abstract class AbstractCRUDService<
        E extends BaseEntity<I>,
        C extends BaseCto,
        F extends SortField,
        I extends Serializable>
        extends AbstractQueryService<E, C, F, I>
        implements CommandService<E, I> {


    @Override
    public E create(E entity) {
        return getRepository().save(entity);
    }

    @Override
    public E update(E entity) throws GlobalException {
        E foundedInDB = fetch(entity.getId());
        edit(foundedInDB, entity);
        return getRepository().save(foundedInDB);
    }

    @Override
    public E remove(E entity) throws GlobalException {
        if (entity == null)
            throw new ResourceNotFoundException(format(VALIDATION_IS_NULL_MSG, getEntityName()));
        E foundedInDB = fetch(entity.getId());
        foundedInDB.setDeleted(true);
        getRepository().save(foundedInDB);
        return foundedInDB;
    }

    protected abstract Class<E> getEntityClass();

    protected abstract void edit(E foundedInDB, E newEntity) throws GlobalException;

}
