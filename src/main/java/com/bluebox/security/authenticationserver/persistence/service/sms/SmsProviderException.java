package com.bluebox.security.authenticationserver.persistence.service.sms;

import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.bluebox.security.authenticationserver.common.Constants.ERROR_PHONE_VERIFICATION;
import static com.bluebox.security.authenticationserver.common.Constants.ERROR_SMS_PROVIDER;


/**
 * @author kamran ghiasvand
 */
public class SmsProviderException extends GlobalException {

    public SmsProviderException(String message) {
        super(ERROR_SMS_PROVIDER, HttpStatus.INTERNAL_SERVER_ERROR);
        this.getMessages().add(message);
    }

    public SmsProviderException(List<String> message) {
        super(ERROR_SMS_PROVIDER, HttpStatus.INTERNAL_SERVER_ERROR);
        this.getMessages().addAll(message);
    }
}
