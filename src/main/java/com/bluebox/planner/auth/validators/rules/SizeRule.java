package com.bluebox.planner.auth.validators.rules;


import com.bluebox.planner.auth.validators.Rule;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.bluebox.planner.auth.common.Constants.VALIDATION_SIZE_MSG;


/**
 * @author kamran ghiasvand
 */
public class SizeRule<T> implements Rule<T> {

    private String field;
    private Function<T, ? extends Collection> supplier;
    private int size;


    public SizeRule(Function<T, ? extends Collection> supplier, String field, int size) {
        this.field = field;
        this.supplier = supplier;
        this.size = size;
    }

    @Override
    public final List<String> validate(T m) {
        Collection apply = supplier.apply(m);
        return apply != null && apply.size() != size ?
                Collections.singletonList(MessageFormat.format(VALIDATION_SIZE_MSG, field, size)) : null;
    }

}
