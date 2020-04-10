package com.bluebox.planner.auth.sms;

import com.bluebox.planner.auth.common.config.AppConfig;
import com.bluebox.planner.auth.persistence.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void sendText(String phoneNumber, String text) {
        LOGGER.debug("sending text '{}' to '{}'", text, phoneNumber);
    }
}
