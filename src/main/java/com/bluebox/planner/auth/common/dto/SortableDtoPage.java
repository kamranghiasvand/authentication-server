package com.bluebox.planner.auth.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Yaser(amin) Sadeghi
 */
@NoArgsConstructor
@Getter
@Setter
public class SortableDtoPage<D extends BaseDto<I>, F extends SortField, I extends Serializable>
        extends SortablePage<F> {

    private D dto;

    public SortableDtoPage(SortablePage<F> page, D dto) {
        super(page, page.getSorts());
        this.dto = dto;
    }

    public SortableDtoPage(SortablePage<F> page) {
        this(page, null);
    }
}
