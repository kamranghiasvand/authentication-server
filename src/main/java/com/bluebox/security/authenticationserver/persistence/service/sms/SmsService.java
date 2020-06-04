package com.bluebox.security.authenticationserver.persistence.service.sms;

import com.bluebox.security.authenticationserver.common.exception.ValidationException;

/**
 * @author by kamran ghiasvand
 */
public interface SmsService {
    void sendText(String phone, String text) throws SmsProviderException, ValidationException;
}
