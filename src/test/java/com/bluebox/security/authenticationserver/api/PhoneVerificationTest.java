package com.bluebox.security.authenticationserver.api;

import com.bluebox.security.authenticationserver.common.config.PhoneVerificationConfig;
import com.bluebox.security.authenticationserver.common.util.RandomStringGen;
import com.bluebox.security.authenticationserver.persistence.repository.PhoneVerificationRepository;
import com.bluebox.security.authenticationserver.persistence.service.SmsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import static com.bluebox.security.authenticationserver.common.Constants.*;
import static com.bluebox.security.authenticationserver.common.PathConstant.REGISTRATION_BASE;
import static com.bluebox.security.authenticationserver.common.PathConstant.SEND_PHONE_VERIFICATION_CODE;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class PhoneVerificationTest {
    private static final String VERIFY_CODE = "123456";
    private final String URL = REGISTRATION_BASE + SEND_PHONE_VERIFICATION_CODE;
    private final String PHONE_PATTERN_INCORRECT = MessageFormat.format(VALIDATION_REGEX_NOT_VALID_MSG, FIELD_USER_PHONE);
    private final String PHONE_IS_NULL = MessageFormat.format(VALIDATION_IS_NULL_MSG, FIELD_USER_PHONE);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PhoneVerificationConfig appConfig;
    @Autowired
    private PhoneVerificationRepository repository;

    @MockBean
    private RandomStringGen randomStringGen;
    @MockBean
    private SmsService smsService;


    @BeforeEach
    public void beforeEach() {
        when(randomStringGen.nextString(Mockito.anyInt())).thenReturn(VERIFY_CODE);
        repository.deleteAll();
    }

    @Test
    @Order(1)
    public void mockIsWorking() {
        String code = randomStringGen.nextString(12);
        Assertions.assertEquals(VERIFY_CODE, code);
    }


    @Test
    @Order(2)
    public void successfulSendVerification() throws Exception {
        final String phoneNum = "+989124651207";
        ArgumentCaptor<String> actualPhone = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> actualCode = ArgumentCaptor.forClass(String.class);
        doNothing().when(smsService).sendText(actualPhone.capture(), actualCode.capture());

        //request for sending verification code
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("phone", phoneNum
        ).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        Assertions.assertEquals(phoneNum, actualPhone.getValue());
        Assertions.assertEquals(VERIFY_CODE, actualCode.getValue());
    }

    @Test
    @Order(3)
    public void twiceSendVerificationException() throws Exception {
        final String phoneNum = "+989124651207";
        ArgumentCaptor<String> actualPhone = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> actualCode = ArgumentCaptor.forClass(String.class);
        doNothing().when(smsService).sendText(actualPhone.capture(), actualCode.capture());

        //request for sending verification code
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("phone", phoneNum)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
        Assertions.assertEquals(phoneNum, actualPhone.getValue());
        Assertions.assertEquals(VERIFY_CODE, actualCode.getValue());

        //request for sending verification code
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("phone", phoneNum)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.key", is(ERROR_PHONE_VERIFICATION)))
                .andExpect(jsonPath("$.messages", hasItem(CODE_ALREADY_SENT_MSG)));

    }

    @Test
    @Order(4)
    public void invalidPhoneNumber() throws Exception {
        final String phoneNum = "invalid";
        //request for sending verification code
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("phone", phoneNum)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.key", is(ERROR_VALIDATION)))
                .andExpect(jsonPath("$.messages", hasItem(PHONE_PATTERN_INCORRECT)));
    }

    @Test
    @Order(5)
    public void nullPhoneNumber() throws Exception {
        //request for sending verification code
        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.key", is(ERROR_VALIDATION)))
                .andExpect(jsonPath("$.messages", hasItem(PHONE_IS_NULL)));
    }

    @Test
    @Order(6)
    public void sendSecondAfterExpireTime() throws Exception {
        appConfig.setExpireTimeSec(1);
        successfulSendVerification();
        TimeUnit.SECONDS.sleep(3);
        successfulSendVerification();

    }

}
