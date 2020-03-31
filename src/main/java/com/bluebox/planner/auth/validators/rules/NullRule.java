package com.bluebox.planner.auth.validators.rules;



import com.bluebox.planner.auth.validators.Rule;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.bluebox.planner.auth.common.Constants.VALIDATION_IS_NULL_MSG;
import static java.text.MessageFormat.format;


/**
 * @author kamran ghiasvand
 */
public class NullRule<T> implements Rule<T> {
    private Function<T, ?> supplier;
    private String field;

    public NullRule(Function<T, ?> supplier, String field) {
        this.supplier = supplier;
        this.field = field;
    }

    @Override
    public final List<String> validate(T t) {
        return supplier.apply(t) == null ? Collections.singletonList(format(VALIDATION_IS_NULL_MSG, field)) : null;
    }

}
