package com.bluebox.planner.auth.common.exception;

import com.bluebox.planner.auth.common.Constants;
import org.springframework.http.HttpStatus;

/**
 * @author kamran ghiasvand
 */
public class UnAuthorizationException extends GlobalException {

    public UnAuthorizationException(String message) {
        super(Constants.ERROR_UN_AUTHORIZED, HttpStatus.FORBIDDEN);
        this.getMessages().add(message);
    }

}
