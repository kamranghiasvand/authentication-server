package com.bluebox.security.authenticationserver.validators;

import com.bluebox.security.authenticationserver.common.exception.ValidationException;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author kamran ghiasvand
 */
@NoArgsConstructor
public class ValidationContext<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationContext.class);
    private List<Rule<T>> validations = new ArrayList<>();

    @SafeVarargs
    public ValidationContext(Rule<T>... rules) {
        addAll(rules);
    }

    public void validate(T t) throws ValidationException {
        LOGGER.trace("Starting validation");
        for (Rule<T> validate : validations) {
            List<String> validate1 = validate.validate(t);
            if (validate1 != null && !validate1.isEmpty())
                throw new ValidationException(validate1);
        }
    }

    public void add(Rule<T> v) {
        validations.add(v);
    }

    @SafeVarargs
    public final void addAll(Rule<T>... rules) {
        if (rules == null)
            throw new NullPointerException("rule is null");
        Collections.addAll(validations, rules);
    }
}
