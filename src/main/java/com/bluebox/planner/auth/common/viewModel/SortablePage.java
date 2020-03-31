package com.bluebox.planner.auth.common.viewModel;

import com.bluebox.planner.auth.common.viewModel.views.ViewMain;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yaser(amin) Sadeghi
 */
@NoArgsConstructor
@Getter
@Setter
public class SortablePage<F extends SortField> extends CustomPage {
    private List<SortBy<F>> sorts = new ArrayList<>();

    public SortablePage(CustomPage page, List<SortBy<F>> sorts) {
        super(page.getStart(), page.getSize());
        this.sorts = sorts;
    }

    public SortablePage(CustomPage page) {
        this(page,null);
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class SortBy<F> {
        public SortBy(F fieldName, Sort.Direction direction) {
            this.fieldName = fieldName;
            this.direction = direction;
        }

        @JsonView(ViewMain.Query.class)
        private F fieldName;
        @JsonView(ViewMain.Query.class)
        private Sort.Direction direction;
    }
}
