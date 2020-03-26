package com.bluebox.planner.auth.persistence.service.base;

import com.bluebox.planner.auth.common.Constants;
import com.bluebox.planner.auth.common.dto.*;
import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.exception.ResourceNotFoundException;
import com.bluebox.planner.auth.common.util.ConvertUtil;
import com.bluebox.planner.auth.persistence.entity.BaseEntity;
import com.bluebox.planner.auth.persistence.repository.BaseRepository;
import com.bluebox.planner.auth.persistence.service.base.enums.IDSortFields;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Transactional(readOnly = true)
public abstract class AbstractQueryService<E extends BaseEntity<I>, D extends BaseDto<I>,
        F extends SortField, I extends Serializable>
        implements QueryService<E, D, F, I> {

    @Override
    public <K> E get(K key, Function<K, Optional<E>> extractor) throws GlobalException {
        if (key == null)
            throw new ResourceNotFoundException(String.format(Constants.VALIDATION_IS_NULL_OR_NEGATIVE_MSG, getEntityName() + " key"));
        Optional<E> byId = extractor.apply(key);
        if (byId.isEmpty())
            throw new ResourceNotFoundException(String.format(Constants.VALIDATION_NOT_FOUND_MSG, getEntityName(), key));
        return byId.get();

    }

    @Override
    public PaginatedResultDTO<E> search(SortableDtoPage<D, F, I> dto) {

        if (dto == null) {
            return defaultResponse();
        }
        if (dto.getDto() == null) {
            return respondWithCustomSort(dto);
        }
        return respondWithCustomDto(dto);
    }
    public <H extends BaseEntity, G extends BaseDto<I>> D fetch(I id, PropertyMap<H, G> propertyMap) throws GlobalException {
        E byId = get(id, this::extract);
        return ConvertUtil.to(propertyMap, byId, getDTOClass());
    }


    private PaginatedResultDTO<E> respondWithCustomDto(SortableDtoPage<D, F, I> dto) {
        Page<E> all;
        PaginatedResultDTO<E> resp = new PaginatedResultDTO<>();
        PageRequest pageable;
        if (!dto.getSorts().isEmpty()) {
            pageable = generatePageRequest(dto.getStart(), dto.getSize(), Sort.by(getOrders(dto.getSorts())));
        } else {
            pageable = generatePageRequest(dto.getStart(), dto.getSize());
        }
        all = searchBy(dto.getDto(), pageable);
        resp.setTotalElements(all.getTotalElements());
        resp.setResults(all.getContent());
        return resp;
    }

    private PaginatedResultDTO<E> respondWithCustomSort(SortableDtoPage<D, F, I> dto) {
        Page<E> all;
        PaginatedResultDTO<E> resp = new PaginatedResultDTO<>();
        Sort sort = Sort.by(getOrders(getDefaultSort()));
        if (dto.getSorts() != null && !dto.getSorts().isEmpty()) {
            List<SortableDtoPage.SortBy<F>> sorts = dto.getSorts();
            sort = Sort.by(getOrders(sorts));
        }
        all = getRepository().findAll(generatePageRequest(dto.getStart(), dto.getSize(), sort));
        resp.setResults(all.getContent());
        resp.setTotalElements(all.getTotalElements());
        return resp;
    }

    private PaginatedResultDTO<E> defaultResponse() {
        PaginatedResultDTO<E> resp = new PaginatedResultDTO<>();
        List<E> list = getRepository().findAll(Sort.by(getOrders(getDefaultSort())));
        resp.setResults(list);
        resp.setTotalElements(list.size());
        return resp;
    }




    private <H extends SortField> List<Sort.Order> getOrders(List<SortableDtoPage.SortBy<H>> sortByList) {
        List<Sort.Order> orders = new ArrayList<>();
        for (SortableDtoPage.SortBy<H> sort : sortByList) {
            if (sort.getDirection().equals(Sort.Direction.ASC))
                orders.add(Sort.Order.asc(sort.getFieldName().getName()));
            else
                orders.add(Sort.Order.desc(sort.getFieldName().getName()));
        }
        return orders;
    }

    private List<SortableDtoPage.SortBy<SortField>> getDefaultSort() {
        SortField id = IDSortFields.ID;
        SortableDtoPage.SortBy<SortField> idSortFieldsSortBy = new SortableDtoPage.SortBy<>(id, Sort.Direction.DESC);
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

    protected Page<E> searchBy(D dto, PageRequest pageable) {
        throw new AbstractMethodError("Could not implemented");
    }

    public abstract BaseRepository<E, ?> getRepository();

    public abstract String getEntityName();

    protected abstract Class<D> getDTOClass();

    protected abstract Optional<E> extract(I key);
}
