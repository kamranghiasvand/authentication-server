package com.bluebox.security.authenticationserver.api.base;

import com.bluebox.security.authenticationserver.api.validation.ValidationFactory;
import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.common.viewModel.BaseCto;
import com.bluebox.security.authenticationserver.common.viewModel.BaseDto;
import com.bluebox.security.authenticationserver.common.viewModel.SortField;
import com.bluebox.security.authenticationserver.persistence.entity.BaseDomainEntity;
import com.bluebox.security.authenticationserver.persistence.service.base.CommandService;

import java.io.Serializable;


/**
 * @author kamran ghiasvand
 */
public abstract class BaseCRUDController<
        E extends BaseDomainEntity<I>,
        D extends BaseDto<I>,
        C extends BaseCto,
        F extends SortField,
        I extends Serializable> extends BaseQueryController<E, D, C, F, I> {


    protected abstract CommandService<E, I> getCommandService();

    protected abstract ValidationFactory<D, I> getValidationFactory();

    protected D add(D dto) throws GlobalException {
        getLogger().info("Creating {}", getEntityLabel());
        getValidationFactory().getCreateCtx().validate(dto);
        E added = getCommandService().create(getConverter().convert(dto));
        getLogger().info("{} created", getEntityLabel());
        return getConverter().convert(added);
    }

    protected D edit(D dto) throws GlobalException {
        getLogger().info("Updating {} '{}'", getEntityLabel(), dto);
        getValidationFactory().getUpdateCtx().validate(dto);
        E updated = getCommandService().update(getConverter().convert(dto));
        getLogger().info("{} with id '{}' updated", getEntityLabel(), dto.getId());
        return getConverter().convert(updated);
    }

    protected D remove(I id) throws GlobalException {
        getLogger().info("Deleting {} with id '{}'", getEntityLabel(), id);
        E delete = getCommandService().remove(id);
        getLogger().info("{} with id '{}' deleted", getEntityLabel(), id);
        return getConverter().convert(delete);
    }

}
