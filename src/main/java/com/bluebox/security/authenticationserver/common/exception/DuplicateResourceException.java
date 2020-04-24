package com.bluebox.security.authenticationserver.common.exception;

import org.springframework.http.HttpStatus;

import static com.bluebox.security.authenticationserver.common.Constants.ERROR_DUPLICATE;

/**
 * @author Yaser(amin) Sadeghi
 */
public class DuplicateResourceException extends GlobalException {

    public DuplicateResourceException(String message) {
        super(ERROR_DUPLICATE, HttpStatus.CONFLICT);
        this.getMessages().add(message);
    }
}
