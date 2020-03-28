package com.bluebox.planner.auth.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

import static com.bluebox.planner.auth.common.Constants.ERROR_VALIDATION;


/**
 * @author Yaser(amin) Sadeghi
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
