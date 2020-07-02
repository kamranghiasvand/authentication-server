package com.bluebox.security.authenticationserver.persistence.service.sms;

import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
    @MockBean
    private RestTemplate restTemplate;
    @Autowired
    private SmsService smsService;

    @BeforeEach
    public void beforeEach() {
        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), Mockito.any()))
                .thenAnswer(invocationOnMock -> new ResponseEntity<>(HttpStatus.OK));
    }

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
    @Order(6)
    public void emptyText() {
        final var exception = Assertions.assertThrows(GlobalException.class, () -> smsService.sendText(phone, ""));
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertNotEquals(0, exception.getMessages());
        final var message = exception.getMessages().get(0);
        Assertions.assertEquals(format(VALIDATION_IS_EMPTY_MSG, TEXT_FIELD_NAME), message);
    }

    @Test
    @Order(7)
    public void notExistPhone() {
        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), Mockito.any()))
                .thenAnswer(invocationOnMock -> {
                    throw new RestClientException("time out");
                });
        final var exception = Assertions.assertThrows(GlobalException.class, () -> smsService.sendText("+980000000001", smsContent));
        Assertions.assertNotNull(exception);

    }


}
