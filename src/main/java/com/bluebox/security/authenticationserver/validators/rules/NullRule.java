package com.bluebox.security.authenticationserver.validators.rules;



import com.bluebox.security.authenticationserver.validators.Rule;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.bluebox.security.authenticationserver.common.Constants.VALIDATION_IS_NULL_MSG;


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
        return supplier.apply(t) == null ? Collections.singletonList(MessageFormat.format(VALIDATION_IS_NULL_MSG, field)) : null;
    }

}
