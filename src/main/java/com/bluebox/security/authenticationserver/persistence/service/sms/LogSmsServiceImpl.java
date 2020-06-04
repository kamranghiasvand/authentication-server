package com.bluebox.security.authenticationserver.persistence.service.sms;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author by kamran ghiasvand
 */
@Service
@ConditionalOnProperty(name = "app.production-mode", havingValue = "false")
public class LogSmsServiceImpl implements SmsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogSmsServiceImpl.class);

    @Override
    public void sendText(String phone, String text) {
        LOGGER.info("sending text '{}' to '{}'", text, phone);
    }
}
