package com.bluebox.security.authenticationserver.persistence.service.sms;

import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

import static com.bluebox.security.authenticationserver.common.Constants.*;
import static java.text.MessageFormat.format;
import static org.mockito.Mockito.*;

/**
 * @author by kamran ghiasvand
 */
@SpringBootTest(properties = {"app.production-mode=false"})
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class LogSmsServiceTest {
    private final String smsContent = "this is a test message from auth-server MeliPaymentSmsServiceTest";
    private final String phone = "+989333938680";
    private final String TEXT_FIELD_NAME = "text";
    private final String PHONE_FIELD_NAME = "phone";

    @Mock
    private Appender mockAppender;
    @Captor
    private ArgumentCaptor<LogEvent> captorLoggingEvent;
    private Logger logger;
    @Autowired
    private SmsService smsService;

    @BeforeEach
    public void beforeEach() {
        when(mockAppender.getName()).thenReturn("MockAppender");
        when(mockAppender.isStarted()).thenReturn(true);
        when(mockAppender.isStopped()).thenReturn(false);
        logger = (Logger) LogManager.getLogger(LogSmsServiceImpl.class);
        logger.addAppender(mockAppender);
        logger.setLevel(Level.INFO);
    }

    @Test
    @Order(1)
    public void loggingIsWorking() throws GlobalException {
        smsService.sendText(phone, smsContent);
        verify(mockAppender,times(1)).append(captorLoggingEvent.capture());
        Assertions.assertEquals(1,captorLoggingEvent.getAllValues().size());
        Assertions.assertEquals(MessageFormat.format("sending text ''{0}'' to ''{1}''",smsContent,phone),
                captorLoggingEvent.getAllValues().get(0).getMessage().getFormattedMessage());
    }



}
