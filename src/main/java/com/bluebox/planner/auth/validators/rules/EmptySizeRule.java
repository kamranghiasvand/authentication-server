package com.bluebox.planner.auth.validators.rules;



import com.bluebox.planner.auth.validators.Rule;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.bluebox.planner.auth.common.Constants.VALIDATION_IS_EMPTY_MSG;


/**
 * @author kamran ghiasvand
 */
public class EmptySizeRule<T> implements Rule<T> {

    private String field;
    private Function<T, ? extends Collection> supplier;


    public EmptySizeRule(Function<T, ? extends Collection> supplier, String field) {
        this.field = field;
        this.supplier = supplier;
    }

    @Override
    public final List<String> validate(T m) {
        Collection apply = supplier.apply(m);
        return apply != null && apply.isEmpty() ? Collections.singletonList(MessageFormat.format(VALIDATION_IS_EMPTY_MSG, field)) : null;
    }

}
