package com.bluebox.planner.auth.persistence.service;

import com.bluebox.planner.auth.common.exception.GlobalException;

/**
 * @author by kamran ghiasvand
 */
public interface SmsService {
    void sendText(String phoneNumber, String text) throws GlobalException;
}
