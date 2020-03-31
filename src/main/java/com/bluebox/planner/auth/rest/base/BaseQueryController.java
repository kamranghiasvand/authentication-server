package com.bluebox.planner.auth.rest.base;

import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.exception.ValidationException;
import com.bluebox.planner.auth.common.util.ConvertUtil;
import com.bluebox.planner.auth.common.viewModel.SortField;
import com.bluebox.planner.auth.common.viewModel.BaseCto;
import com.bluebox.planner.auth.common.viewModel.SortablePageCto;
import com.bluebox.planner.auth.common.viewModel.BaseDto;
import com.bluebox.planner.auth.common.viewModel.CustomPage;
import com.bluebox.planner.auth.common.viewModel.PaginatedResultDto;
import com.bluebox.planner.auth.common.viewModel.SortablePage;
import com.bluebox.planner.auth.persistence.entity.BaseEntity;
import com.bluebox.planner.auth.persistence.service.base.QueryService;
import com.bluebox.planner.auth.persistence.service.base.SearchResult;
import org.slf4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author kamran ghiasvand
 */
public abstract class BaseQueryController<
        E extends BaseEntity<I>,
        D extends BaseDto<I>,
        C extends BaseCto,
        F extends SortField,
        I extends Serializable> {

    protected abstract QueryService<E,C, F, I> getQueryService();

    protected E fetch(I id) throws GlobalException {
        getLogger().info("Fetching {} with id '{}'", getEntityLabel(), id);
        return getQueryService().fetch(id);
    }

    protected PaginatedResultDto<D,I> query(SortablePageCto<C, F> page) throws ValidationException {
        validatePaging(page);
        getLogger().info("Fetching all {} ", getEntityLabel());
        SearchResult<E, I> entities = getQueryService().search(page);
        getLogger().info("{} size is '{}' ", getEntityLabel(), entities.getResults().size());
        PaginatedResultDto<D,I> dtoList = new PaginatedResultDto<>();
        dtoList.setResults(new ArrayList<>());
        dtoList.setTotalElements(entities.getTotalElements());
        dtoList.getResults().addAll(ConvertUtil.to(entities.getResults(), getDTOClass()));
        return dtoList;
    }

    protected PaginatedResultDto<D,I> query(CustomPage page) throws ValidationException {
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
