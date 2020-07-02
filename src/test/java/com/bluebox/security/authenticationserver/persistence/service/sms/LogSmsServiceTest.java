package com.bluebox.security.authenticationserver.persistence.service.sms;

import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static com.bluebox.security.authenticationserver.common.Constants.*;
import static java.text.MessageFormat.format;

/**
 * @author by kamran ghiasvand
 */
@SpringBootTest(properties = {"app.production-mode=true"})
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class MeliPaymentSmsServiceTest {
    private final String smsContent = "this is a test message from auth-server MeliPaymentSmsServiceTest";
    private final String phone = "+989333938680";
    private final String TEXT_FIELD_NAME = "text";
    private final String PHONE_FIELD_NAME = "phone";

    @Autowired
    private SmsService smsService;

    @Test
    @Order(1)
    public void successfullySend() throws GlobalException {
        smsService.sendText(phone, smsContent);
    }

    @Test
    @Order(2)
    public void nullPhone() {
        final var exception = Assertions.assertThrows(GlobalException.class, () -> smsService.sendText(null, smsContent));
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertNotEquals(0, exception.getMessages());
        final var message = exception.getMessages().get(0);
        Assertions.assertEquals(format(VALIDATION_IS_NULL_MSG, PHONE_FIELD_NAME), message);
    }

    @Test
    @Order(3)
    public void emptyPhone() {
        final var exception = Assertions.assertThrows(GlobalException.class, () -> smsService.sendText("", smsContent));
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertNotEquals(0, exception.getMessages());
        final var message = exception.getMessages().get(0);
        Assertions.assertEquals(format(VALIDATION_REGEX_NOT_VALID_MSG, PHONE_FIELD_NAME), message);
    }

    @Test
    @Order(4)
    public void invalidPhone() {
        final var exception = Assertions.assertThrows(GlobalException.class, () -> smsService.sendText("invalid", smsContent));
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertNotEquals(0, exception.getMessages());
        final var message = exception.getMessages().get(0);
        Assertions.assertEquals(format(VALIDATION_REGEX_NOT_VALID_MSG, PHONE_FIELD_NAME), message);
    }

    @Test
    @Order(5)
    public void nullText() {
        final var exception = Assertions.assertThrows(GlobalException.class, () -> smsService.sendText(phone, null));
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertNotEquals(0, exception.getMessages());
        final var message = exception.getMessages().get(0);
        Assertions.assertEquals(format(VALIDATION_IS_NULL_MSG, TEXT_FIELD_NAME), message);
    }

    @Test
    @Order(3)
    public void emptyText() {
        final var exception = Assertions.assertThrows(GlobalException.class, () -> smsService.sendText(phone, ""));
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertNotEquals(0, exception.getMessages());
        final var message = exception.getMessages().get(0);
        Assertions.assertEquals(format(VALIDATION_IS_EMPTY_MSG, TEXT_FIELD_NAME), message);
    }


}
