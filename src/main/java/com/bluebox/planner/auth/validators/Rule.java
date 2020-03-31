package com.bluebox.planner.auth.validators;

import java.util.List;

/**
 * @author kamran ghiasvand
 */
@FunctionalInterface
public interface Rule<T> {
    List<String> validate(T t);
}
