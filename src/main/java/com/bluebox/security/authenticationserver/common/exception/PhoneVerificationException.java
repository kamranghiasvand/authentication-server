package com.bluebox.security.authenticationserver.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

import static com.bluebox.security.authenticationserver.common.Constants.ERROR_PHONE_VERIFICATION;


/**
 * @author kamran ghiasvand
 */
public class PhoneVerificationException extends GlobalException {

    public PhoneVerificationException(String message) {
        super(ERROR_PHONE_VERIFICATION, HttpStatus.BAD_REQUEST);
        this.getMessages().add(message);
    }

    public PhoneVerificationException(List<String> message) {
        super(ERROR_PHONE_VERIFICATION, HttpStatus.BAD_REQUEST);
        this.getMessages().addAll(message);
    }
}
