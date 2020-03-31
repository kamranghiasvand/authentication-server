package com.bluebox.planner.auth.validators.rules;


import java.util.function.Function;


/**
 * @author kamran ghiasvand
 */
public class EmailRule<T> extends PatternRule<T> {

    public EmailRule(Function<T, String> supplier, String field) {
        super(supplier, "^(.+)@(.+)$", field);
    }
}
