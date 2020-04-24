package com.bluebox.security.authenticationserver.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

import static com.bluebox.security.authenticationserver.common.Constants.ERROR_CONVERTER;


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
