package com.bluebox.security.authenticationserver.validators.rules;


import com.bluebox.security.authenticationserver.validators.Rule;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.bluebox.security.authenticationserver.common.Constants.VALIDATION_MULTI_IS_NULL_MSG;


/**
 * @author kamran ghiasvand
 */
public class MultiNullRule<T> implements Rule<T> {

    private final String fields;
    private final Function<T, ?>[] suppliers;

    public MultiNullRule(String fields, Function<T, ?>... suppliers) {
        this.fields = fields;
        this.suppliers = suppliers;
    }


    @Override
    public List<String> validate(T t) {
        if (suppliers == null)
            return null;
        for (Function<T, ?> supplier : suppliers) {
            if (supplier.apply(t) != null)
                return null;
        }
        return Collections.singletonList(MessageFormat.format(VALIDATION_MULTI_IS_NULL_MSG, fields));
    }
}
