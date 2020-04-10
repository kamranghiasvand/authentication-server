package com.bluebox.planner.auth.persistence.service.base;


import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.viewModel.SortField;
import com.bluebox.planner.auth.common.viewModel.BaseCto;
import com.bluebox.planner.auth.common.viewModel.SortablePageCto;
import com.bluebox.planner.auth.persistence.entity.BaseDomainEntity;
import com.bluebox.planner.auth.persistence.entity.BaseEntity;

import java.io.Serializable;

/**
 * @author kamran ghiasvand
 */
public interface QueryService<
        E extends BaseEntity<I>,
        C extends BaseCto,
        F extends SortField, I extends Serializable> {
    E fetch(I key) throws GlobalException;
    SearchResult<E,I> search(SortablePageCto<C, F> criteria);
}
