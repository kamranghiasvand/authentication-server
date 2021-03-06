package com.bluebox.security.authenticationserver.common.viewModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Yaser(amin) Sadeghi
 */
@NoArgsConstructor
@Getter
@Setter
public class SortablePageCto<C extends BaseCto, F extends SortField>
        extends SortablePage<F> {

    private C cto;

    public SortablePageCto(SortablePage<F> page, C cto) {
        super(page, page.getSorts());
        this.cto = cto;
    }

    public SortablePageCto(SortablePage<F> page) {
        this(page, null);
    }
}
