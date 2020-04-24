package com.bluebox.security.authenticationserver.persistence.service;

import com.bluebox.security.authenticationserver.common.exception.GlobalException;

/**
 * @author by kamran ghiasvand
 */
public interface SmsService {
    void sendText(String phoneNumber, String text) throws GlobalException;
}
