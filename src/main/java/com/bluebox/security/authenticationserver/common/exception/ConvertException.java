package com.bluebox.security.authenticationserver.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

import static com.bluebox.security.authenticationserver.common.Constants.ERROR_CONVERTER;


/**
 * @author kamran ghiasvand
 */
public class ConvertException extends GlobalException {

    public ConvertException(String message) {
        super(ERROR_CONVERTER, HttpStatus.INTERNAL_SERVER_ERROR);
        this.getMessages().add(message);
    }

    public ConvertException(List<String> message) {
        super(ERROR_CONVERTER, HttpStatus.INTERNAL_SERVER_ERROR);
        this.getMessages().addAll(message);
    }
}
