package com.bluebox.security.authenticationserver.common.exception;

import org.springframework.http.HttpStatus;

import static com.bluebox.security.authenticationserver.common.Constants.ERROR_NOT_FOUND;

/**
 * @author kamran ghiasvand
 */
public class ResourceNotFoundException extends GlobalException {

    public ResourceNotFoundException(String message) {
        super(ERROR_NOT_FOUND, HttpStatus.NOT_FOUND);
        this.getMessages().add(message);
    }

}
