package com.bluebox.security.authenticationserver.validators;

import java.util.List;

/**
 * @author kamran ghiasvand
 */
@FunctionalInterface
public interface Rule<T> {
    List<String> validate(T t);
}
