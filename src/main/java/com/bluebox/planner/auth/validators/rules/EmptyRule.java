package com.bluebox.planner.auth.validators.rules;



import com.bluebox.planner.auth.validators.Rule;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.bluebox.planner.auth.common.Constants.VALIDATION_IS_EMPTY_MSG;


/**
 * @author kamran ghiasvand
 */
public class EmptyRule<T> implements Rule<T> {
    private Function<T, String> supplier;
    private String field;

    public EmptyRule(Function<T, String> supplier, String field) {
        this.supplier = supplier;
        this.field = field;
    }

    @Override
    public final List<String> validate(T t) {
        String apply = supplier.apply(t);
        return apply != null && "".equals(apply.trim()) ? Collections.singletonList(MessageFormat.format(VALIDATION_IS_EMPTY_MSG, field)) : null;
    }

}
