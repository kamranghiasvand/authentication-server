package com.bluebox.security.authenticationserver.validators.rules;


import com.bluebox.security.authenticationserver.validators.Rule;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

import static com.bluebox.security.authenticationserver.common.Constants.VALIDATION_REGEX_NOT_VALID_MSG;
import static java.text.MessageFormat.format;


/**
 * @author kamran ghiasvand
 */
public class PatternRuleEmpty<T> implements Rule<T> {

    private Pattern pattern;
    private Function<T, String> supplier;
    private String field;

    public PatternRuleEmpty(Function<T, String> supplier, String regex, String field) {
        this.field = field;
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        this.supplier = supplier;
    }

    @Override
    public final List<String> validate(T m) {
        String apply = supplier.apply(m);
        return apply != null && !"".equals(apply) ? pattern.matcher(apply.trim()).find() ? null :
                Collections.singletonList(format(VALIDATION_REGEX_NOT_VALID_MSG, field)) : null;
    }
}
