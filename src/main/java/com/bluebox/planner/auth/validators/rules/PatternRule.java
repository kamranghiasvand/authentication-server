package com.bluebox.planner.auth.validators.rules;


import com.bluebox.planner.auth.validators.Rule;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

import static com.bluebox.planner.auth.common.Constants.VALIDATION_REGEX_NOT_VALID_MSG;


/**
 * @author kamran ghiasvand
 */
public class PatternRule<T> implements Rule<T> {

    private Pattern pattern;
    private Function<T, String> supplier;
    private String field;

    public PatternRule(Function<T, String> supplier, String regex, String field) {
        this.field = field;
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        this.supplier = supplier;
    }

    @Override
    public final List<String> validate(T m) {
        String apply = supplier.apply(m);
        return apply != null ? pattern.matcher(apply.trim()).find() ? null :
                Collections.singletonList(MessageFormat.format(VALIDATION_REGEX_NOT_VALID_MSG, field)) : null;
    }
}
