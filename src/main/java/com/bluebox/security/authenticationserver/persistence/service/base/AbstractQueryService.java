package com.bluebox.security.authenticationserver.persistence.service.base;

import com.bluebox.security.authenticationserver.common.exception.ResourceNotFoundException;
import com.bluebox.security.authenticationserver.common.viewModel.BaseCto;
import com.bluebox.security.authenticationserver.common.viewModel.SortField;
import com.bluebox.security.authenticationserver.common.viewModel.SortablePageCto;
import com.bluebox.security.authenticationserver.persistence.entity.BaseEntity;
import com.bluebox.security.authenticationserver.persistence.repository.BaseRepository;
import com.bluebox.security.authenticationserver.persistence.service.base.enums.IDSortFields;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.bluebox.security.authenticationserver.common.Constants.VALIDATION_IS_NULL_OR_NEGATIVE_MSG;
import static com.bluebox.security.authenticationserver.common.Constants.VALIDATION_NOT_FOUND_MSG;
import static java.text.MessageFormat.format;

@Transactional(readOnly = true)
public abstract class AbstractQueryService<
        E extends BaseEntity<I>,
        C extends BaseCto,
        F extends SortField,
        I extends Serializable>
        implements QueryService<E, C, F, I> {

    @Override
    public E fetch(I key) {
        if (key == null)
            throw new ResourceNotFoundException(format(VALIDATION_IS_NULL_OR_NEGATIVE_MSG, getEntityName() + " key"));
        Optional<E> byId = getRepository().findById(key);
        if (byId.isEmpty())
            throw new ResourceNotFoundException(format(VALIDATION_NOT_FOUND_MSG, getEntityName(), key));
        final var entity = byId.get();
        if (entity.getDeleted())
            throw new ResourceNotFoundException(format(VALIDATION_NOT_FOUND_MSG, getEntityName(), key));
        return entity;
    }


    @Override
    public SearchResult<E, I> search(SortablePageCto<C, F> criteria) {

        if (criteria == null) {
            return defaultResponse();
        }
        if (criteria.getCto() == null) {
            return respondWithCustomSort(criteria);
        }
        return respondWithCustomDto(criteria);
    }


    private SearchResult<E, I> respondWithCustomDto(SortablePageCto<C, F> dto) {
        Page<E> all;
        SearchResult<E, I> resp = new SearchResult<>();
        PageRequest pageable;
        if (!dto.getSorts().isEmpty()) {
            pageable = generatePageRequest(dto.getStart(), dto.getSize(), Sort.by(getOrders(dto.getSorts())));
        } else {
            pageable = generatePageRequest(dto.getStart(), dto.getSize());
        }
        BaseSpec<E> spec = getSpec(dto.getCto());
        all = getRepository().findAll(spec.get(), pageable);
        resp.setTotalElements(all.getTotalElements());
        resp.setResults(all.getContent());
        return resp;
    }

    private SearchResult<E, I> respondWithCustomSort(SortablePageCto<C, F> criteria) {
        Page<E> all;
        SearchResult<E, I> resp = new SearchResult<>();
        Sort sort = Sort.by(getOrders(getDefaultSort()));
        if (criteria.getSorts() != null && !criteria.getSorts().isEmpty()) {
            List<SortablePageCto.SortBy<F>> sorts = criteria.getSorts();
            sort = Sort.by(getOrders(sorts));
        }
        all = getRepository().findAll(generatePageRequest(criteria.getStart(), criteria.getSize(), sort));
        resp.setResults(all.getContent());
        resp.setTotalElements(all.getTotalElements());
        return resp;
    }

    private SearchResult<E, I> defaultResponse() {
        SearchResult<E, I> resp = new SearchResult<>();
        List<E> list = getRepository().findAll(Sort.by(getOrders(getDefaultSort())));
        resp.setResults(list);
        resp.setTotalElements(list.size());
        return resp;
    }


    private <H extends SortField> List<Sort.Order> getOrders(List<SortablePageCto.SortBy<H>> sortByList) {
        List<Sort.Order> orders = new ArrayList<>();
        for (SortablePageCto.SortBy<H> sort : sortByList) {
            if (sort.getDirection().equals(Sort.Direction.ASC))
                orders.add(Sort.Order.asc(sort.getFieldName().getName()));
            else
                orders.add(Sort.Order.desc(sort.getFieldName().getName()));
        }
        return orders;
    }

    private List<SortablePageCto.SortBy<SortField>> getDefaultSort() {
        SortField id = IDSortFields.ID;
        SortablePageCto.SortBy<SortField> idSortFieldsSortBy = new SortablePageCto.SortBy<>(id, Sort.Direction.DESC);
        return Collections.singletonList(idSortFieldsSortBy);
    }


    private PageRequest generatePageRequest(Integer first, Integer count, Sort sort) {
        if (first == null)
            first = 0;
        if (count == null)
            count = 50;
        int div = first % count;
        int page = first / count;
        if (div > 0) {
            page++;
        }
        return PageRequest.of(page, count, sort);
    }

    private PageRequest generatePageRequest(Integer first, Integer count) {
        if (count == null) {
            count = 50;
        }
        if (first == null)
            first = 0;
        int div = first % count;
        int page = first / count;
        if (div > 0) {
            page++;
        }
        return PageRequest.of(page, count);
    }

    protected abstract BaseSpec<E> getSpec(C criteria);

    public abstract BaseRepository<E, I> getRepository();

    public abstract String getEntityName();
}
