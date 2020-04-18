package com.bluebox.planner.auth.rest;

import com.bluebox.planner.auth.common.Constants;
import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.persistence.service.PhoneVerificationService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.MessageFormat;

import static com.bluebox.planner.auth.common.Constants.*;
import static com.bluebox.planner.auth.common.PathConstant.REGISTRATION_BASE;
import static com.bluebox.planner.auth.common.PathConstant.SEND_PHONE_VERIFICATION_CODE;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class SendVerificationCodeTest {
    private static final String URL = REGISTRATION_BASE + SEND_PHONE_VERIFICATION_CODE;
    private final String PHONE_IS_NULL = MessageFormat.format(VALIDATION_IS_NULL_MSG, FIELD_USER_PHONE);
    private final String PHONE_PATTERN_INCORRECT = MessageFormat.format(VALIDATION_REGEX_NOT_VALID_MSG, FIELD_USER_PHONE);
    private final String PARAM_NAME = "phone";

    private ArgumentCaptor<String> actualPhone = ArgumentCaptor.forClass(String.class);

    @MockBean
    private PhoneVerificationService phoneVerificationService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() throws GlobalException {
        Mockito.doNothing().when(phoneVerificationService).send(actualPhone.capture());
    }

    @Test
    @Order(1)
    public void successSend() throws Exception {
        final var phoneNum = "+989333938680";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param(PARAM_NAME, phoneNum
        ).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        Assertions.assertEquals(phoneNum, actualPhone.getValue());
    }

    @Test
    @Order(2)
    public void failNullPhone() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.key", is(Constants.ERROR_VALIDATION)))
                .andExpect(jsonPath("$.messages", hasItem(PHONE_IS_NULL)));
    }

    @Test
    @Order(3)
    public void failEmptyPhone() throws Exception {
        final var phoneNum = "";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param(PARAM_NAME, phoneNum).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.key", is(Constants.ERROR_VALIDATION)))
                .andExpect(jsonPath("$.messages", hasItem(PHONE_PATTERN_INCORRECT)));
    }

    @Test
    @Order(4)
    public void failInvalidPhone() throws Exception {
        final var phoneNum = "invalid phone";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param(PARAM_NAME, phoneNum).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.key", is(Constants.ERROR_VALIDATION)))
                .andExpect(jsonPath("$.messages", hasItem(PHONE_PATTERN_INCORRECT)));
    }

    @Test
    @Order(5)
    public void failLocalPhone() throws Exception {
        final var phoneNum = "09333938680";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param(PARAM_NAME, phoneNum).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.key", is(Constants.ERROR_VALIDATION)))
                .andExpect(jsonPath("$.messages", hasItem(PHONE_PATTERN_INCORRECT)));
    }

    @Test
    @Order(6)
    public void failUnexpectedException() throws Exception {
        var exception = new InternalException();
        Mockito.doThrow(exception).when(phoneVerificationService).send(actualPhone.capture());
        final var phoneNum = "+989333938680";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param(PARAM_NAME, phoneNum).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.key", is(ERROR_UNHANDLED)));
    }

    public static class InternalException extends GlobalException {

        public InternalException() {
            super(ERROR_UNHANDLED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
