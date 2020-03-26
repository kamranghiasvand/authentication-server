package com.bluebox.planner.auth.persistence.service.base;

import com.bluebox.planner.auth.persistence.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author by kamran ghiasvand
 */
@Getter
@Setter
@NoArgsConstructor
public class SearchResult <E extends BaseEntity<I>,I extends Serializable> {
    private Collection<E> results;
    private long totalElements;
}
