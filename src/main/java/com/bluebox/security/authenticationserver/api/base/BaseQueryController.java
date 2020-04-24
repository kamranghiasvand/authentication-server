package com.bluebox.security.authenticationserver.api.base;

import com.bluebox.security.authenticationserver.api.Converter;
import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.common.exception.ValidationException;
import com.bluebox.security.authenticationserver.common.util.ConvertUtil;
import com.bluebox.security.authenticationserver.common.viewModel.*;
import com.bluebox.security.authenticationserver.persistence.entity.BaseDomainEntity;
import com.bluebox.security.authenticationserver.persistence.service.base.QueryService;
import com.bluebox.security.authenticationserver.persistence.service.base.SearchResult;
import org.slf4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author kamran ghiasvand
 */
public abstract class BaseQueryController<
        E extends BaseDomainEntity<I>,
        D extends BaseDto<I>,
        C extends BaseCto,
        F extends SortField,
        I extends Serializable> {

    protected abstract QueryService<E, C, F, I> getQueryService();

    protected abstract Converter<E, D, I> getConverter();

    protected D fetch(I id) throws GlobalException {
        getLogger().info("Fetching {} with id '{}'", getEntityLabel(), id);
        E fetch = getQueryService().fetch(id);
        return getConverter().convert(fetch);
    }

    protected PaginatedResultDto<D, I> query(SortablePageCto<C, F> page) throws ValidationException {
        validatePaging(page);
        getLogger().info("Fetching all {} ", getEntityLabel());
        SearchResult<E, I> entities = getQueryService().search(page);
        getLogger().info("{} size is '{}' ", getEntityLabel(), entities.getResults().size());
        PaginatedResultDto<D, I> dtoList = new PaginatedResultDto<>();
        dtoList.setResults(new ArrayList<>());
        dtoList.setTotalElements(entities.getTotalElements());
        dtoList.getResults().addAll(ConvertUtil.to(entities.getResults(), getDTOClass()));
        return dtoList;
    }

    protected PaginatedResultDto<D, I> query(CustomPage page) throws ValidationException {
        validatePaging(page);
        return query(new SortablePageCto<>(new SortablePage<>(page)));
    }

    private void validatePaging(CustomPage page) throws ValidationException {
        if (page == null)
            throw new ValidationException("The paging is null");
        if (page.getStart() == null)
            throw new ValidationException("The start of paging is null");
        if (page.getSize() == null)
            throw new ValidationException("The size of paging is null");
    }

    protected abstract String getEntityLabel();

    protected abstract Class<D> getDTOClass();

    protected abstract Logger getLogger();

}
