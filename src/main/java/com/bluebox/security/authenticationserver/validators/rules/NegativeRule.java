package com.bluebox.security.authenticationserver.validators.rules;


import com.bluebox.security.authenticationserver.validators.Rule;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.bluebox.security.authenticationserver.common.Constants.VALIDATION_IS_NEGATIVE_MSG;


/**
 * @author kamran ghiasvand
 */
public class NegativeRule<T> implements Rule<T> {
    private Function<T, ? extends Number> supplier;
    private String field;

    public NegativeRule(Function<T, ? extends Number> supplier, String field) {
        this.supplier = supplier;
        this.field = field;
    }

    @Override
    public final List<String> validate(T t) {
        Number apply = supplier.apply(t);
        return apply != null && apply.longValue() < 1 ? Collections.singletonList(MessageFormat.format(VALIDATION_IS_NEGATIVE_MSG, field)) : null;
    }

}
