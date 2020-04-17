package com.bluebox.planner.auth.integration.base;

import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.viewModel.SortField;
import com.bluebox.planner.auth.common.viewModel.BaseCto;
import com.bluebox.planner.auth.common.viewModel.BaseDto;
import com.bluebox.planner.auth.persistence.entity.BaseDomainEntity;
import com.bluebox.planner.auth.persistence.service.base.CommandService;
import com.bluebox.planner.auth.integration.validation.ValidationFactory;

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
