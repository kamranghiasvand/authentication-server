package com.bluebox.planner.auth.persistence.service;

/**
 * @author by kamran ghiasvand
 */
public interface SmsService {
    void sendText(String phoneNumber,String text);
}
