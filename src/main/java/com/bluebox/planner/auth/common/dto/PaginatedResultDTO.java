package com.bluebox.planner.auth.common.dto;

import com.bluebox.planner.auth.common.dto.views.ViewMain;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

/**
 * @author Yaser(amin) Sadeghi
 */
@NoArgsConstructor
@Getter
@Setter
public class PaginatedResultDTO<T> {
    @JsonView(ViewMain.Query.class)
    private Collection<T> results;
    @JsonView(ViewMain.Query.class)
    private long totalElements;
}
