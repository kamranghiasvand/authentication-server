package com.bluebox.planner.auth.validators.rules;


import com.bluebox.planner.auth.validators.Rule;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.bluebox.planner.auth.common.Constants.VALIDATION_IS_ZERO_MSG;


/**
 * @author kamran ghiasvand
 */
public class ZeroRule<T> implements Rule<T> {
    private Function<T, ? extends Number> supplier;
    private String field;

    public ZeroRule(Function<T, ? extends Number> supplier, String field) {
        this.supplier = supplier;
        this.field = field;
    }

    @Override
    public final List<String> validate(T t) {
        Number apply = supplier.apply(t);
        return apply != null && apply.longValue() == 0 ?
                Collections.singletonList(MessageFormat.format(VALIDATION_IS_ZERO_MSG, field)) : null;
    }

}
