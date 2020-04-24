package com.bluebox.security.authenticationserver.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

import static com.bluebox.security.authenticationserver.common.Constants.ERROR_VALIDATION;


/**
 * @author kamran ghiasvand
 */
public class ValidationException extends GlobalException {

    public ValidationException(String message) {
        super(ERROR_VALIDATION, HttpStatus.BAD_REQUEST);
        this.getMessages().add(message);
    }

    public ValidationException(List<String> message) {
        super(ERROR_VALIDATION, HttpStatus.BAD_REQUEST);
        this.getMessages().addAll(message);
    }
}
