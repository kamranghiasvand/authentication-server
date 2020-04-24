package com.bluebox.security.authenticationserver.validators.rules;



import com.bluebox.security.authenticationserver.validators.Rule;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.bluebox.security.authenticationserver.common.Constants.VALIDATION_LENGTH_MAX_MSG;


/**
 * @author kamran ghiasvand
 */
public class MaxLengthRule<T> implements Rule<T> {

    private int length;
    private String field;
    private Function<T, String> supplier;


    public MaxLengthRule(Function<T, String> supplier, int length, String field) {
        this.length = length;
        this.field = field;
        this.supplier = supplier;
    }

    @Override
    public final List<String> validate(T m) {
        String apply = supplier.apply(m);
        return apply != null && apply.trim().length() > length ? Collections.singletonList(MessageFormat.format(VALIDATION_LENGTH_MAX_MSG, field, length)) : null;
    }

}
