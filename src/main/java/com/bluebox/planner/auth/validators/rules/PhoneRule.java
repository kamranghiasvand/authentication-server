package com.bluebox.planner.auth.validators.rules;


import java.util.function.Function;


/**
 * @author kamran ghiasvand
 */
public class PhoneRule<T> extends PatternRule<T> {

    public PhoneRule(Function<T, String> supplier, String field) {
        super(supplier, "^\\+(?:[0-9]?){11,14}$", field);
    }
}
