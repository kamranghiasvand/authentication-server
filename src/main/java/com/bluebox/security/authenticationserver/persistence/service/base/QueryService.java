package com.bluebox.security.authenticationserver.persistence.service.base;


import com.bluebox.security.authenticationserver.common.viewModel.BaseCto;
import com.bluebox.security.authenticationserver.common.viewModel.SortField;
import com.bluebox.security.authenticationserver.common.viewModel.SortablePageCto;
import com.bluebox.security.authenticationserver.persistence.entity.BaseEntity;

import java.io.Serializable;

/**
 * @author kamran ghiasvand
 */
public interface QueryService<
        E extends BaseEntity<I>,
        C extends BaseCto,
        F extends SortField, I extends Serializable> {
    E fetch(I key);

    SearchResult<E, I> search(SortablePageCto<C, F> criteria);
}
