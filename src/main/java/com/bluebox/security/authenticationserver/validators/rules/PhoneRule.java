package com.bluebox.security.authenticationserver.validators.rules;


import java.util.function.Function;


/**
 * @author kamran ghiasvand
 */
public class PhoneRule<T> extends PatternRule<T> {

    public PhoneRule(Function<T, String> supplier, String field) {
        super(supplier, "^\\+?[1-9]{1}[0-9]{6,14}$", field);
    }
}
