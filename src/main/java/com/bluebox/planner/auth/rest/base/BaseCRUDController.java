package com.bluebox.planner.auth.rest.base;

import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.exception.NotConverterSupportException;
import com.bluebox.planner.auth.common.viewModel.SortField;
import com.bluebox.planner.auth.common.viewModel.BaseCto;
import com.bluebox.planner.auth.common.viewModel.BaseDto;
import com.bluebox.planner.auth.persistence.entity.BaseEntity;
import com.bluebox.planner.auth.persistence.service.base.CommandService;
import com.bluebox.planner.auth.rest.Converter;
import com.bluebox.planner.auth.rest.validation.ValidationFactory;

import java.io.Serializable;


/**
 * @author kamran ghiasvand
 */
public abstract class BaseCRUDController<
        E extends BaseEntity<I>,
        D extends BaseDto<I>,
        C extends BaseCto,
        F extends SortField,
        I extends Serializable> extends BaseQueryController<E, D, C, F, I> {

    protected abstract Converter<E, D, I> getConverter() throws NotConverterSupportException;

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

}
