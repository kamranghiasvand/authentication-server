package com.bluebox.planner.auth.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

import static com.bluebox.planner.auth.common.Constants.ERROR_PHONE_VERIFICATION;
import static com.bluebox.planner.auth.common.Constants.ERROR_VALIDATION;


/**
 * @author kamran ghiasvand
 */
public class PhoneVerificationException extends GlobalException {

    public PhoneVerificationException(String message) {
        super(ERROR_PHONE_VERIFICATION, HttpStatus.FORBIDDEN);
        this.getMessages().add(message);
    }

    public PhoneVerificationException(List<String> message) {
        super(ERROR_PHONE_VERIFICATION, HttpStatus.FORBIDDEN);
        this.getMessages().addAll(message);
    }
}
