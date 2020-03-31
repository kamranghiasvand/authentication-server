package com.bluebox.planner.auth.common.viewModel;

import com.bluebox.planner.auth.common.viewModel.views.ViewMain;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Yaser(amin) Sadeghi
 */
@NoArgsConstructor
@Getter
@Setter
public class PaginatedResultDto<D extends BaseDto<I>,I extends Serializable> {
    @JsonView(ViewMain.Query.class)
    private Collection<D> results;
    @JsonView(ViewMain.Query.class)
    private long totalElements;
}
