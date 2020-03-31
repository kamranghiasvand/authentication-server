package com.bluebox.planner.auth.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

import static com.bluebox.planner.auth.common.Constants.ERROR_CONVERTER;
import static com.bluebox.planner.auth.common.Constants.ERROR_VALIDATION;


/**
 * @author kamran ghiasvand
 */
public class NotConverterSupportException extends GlobalException {

    public NotConverterSupportException(String message) {
        super(ERROR_CONVERTER, HttpStatus.NOT_IMPLEMENTED);
        this.getMessages().add(message);
    }

    public NotConverterSupportException(List<String> message) {
        super(ERROR_CONVERTER, HttpStatus.NOT_IMPLEMENTED);
        this.getMessages().addAll(message);
    }
}
