package com.bluebox.security.authenticationserver.validators.rules;


import com.bluebox.security.authenticationserver.validators.Rule;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @author Kamran Ghiasvand
 */
public class CollectionCustomFieldRule<T, Y> implements Rule<T> {

    private final Function<T, ? extends Collection<Y>> supplier;
    private final Rule<Y> fieldRule;

    public CollectionCustomFieldRule(Function<T, ? extends Collection<Y>> supplier, Rule<Y> fieldRule) {

        this.supplier = supplier;
        this.fieldRule = fieldRule;
    }

    @Override
    public List<String> validate(T t) {
        Collection<Y> collection = supplier.apply(t);
        for (Y elm : collection) {
            List<String> validate = fieldRule.validate(elm);
            if (validate != null)
                return validate;
        }
        return null;
    }
}
