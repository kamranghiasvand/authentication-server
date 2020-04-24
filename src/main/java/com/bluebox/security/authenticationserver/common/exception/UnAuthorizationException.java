package com.bluebox.security.authenticationserver.common.exception;

import org.springframework.http.HttpStatus;

import static com.bluebox.security.authenticationserver.common.Constants.ERROR_UN_AUTHORIZED;

/**
 * @author kamran ghiasvand
 */
public class UnAuthorizationException extends GlobalException {

    public UnAuthorizationException(String message) {
        super(ERROR_UN_AUTHORIZED, HttpStatus.FORBIDDEN);
        this.getMessages().add(message);
    }

}
