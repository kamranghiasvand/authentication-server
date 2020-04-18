package com.bluebox.planner.auth.rest;

import com.bluebox.planner.auth.Builder.RUserDtoBuilder;
import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.exception.PhoneVerificationException;
import com.bluebox.planner.auth.persistence.entity.regular.RegularUserEntity;
import com.bluebox.planner.auth.persistence.service.PhoneVerificationService;
import com.bluebox.planner.auth.persistence.service.RegularUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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

import static com.bluebox.planner.auth.common.Constants.*;
import static com.bluebox.planner.auth.common.PathConstant.REGISTER_WITH_PHONE;
import static com.bluebox.planner.auth.common.PathConstant.REGISTRATION_BASE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class RegisterWithCodeTest {
    private static final String URL = REGISTRATION_BASE + REGISTER_WITH_PHONE;
    private final String PHONE_IS_NULL = MessageFormat.format(VALIDATION_IS_NULL_MSG, FIELD_USER_PHONE);
    private final String CODE_IS_NULL = MessageFormat.format(VALIDATION_IS_NULL_MSG, FIELD_VERIFICATION_CODE);
    private final String PHONE_PATTERN_INCORRECT = MessageFormat.format(VALIDATION_REGEX_NOT_VALID_MSG, FIELD_USER_PHONE);
    private final String PARAM_CODE = "code";

    private ArgumentCaptor<String> actualPhone = ArgumentCaptor.forClass(String.class);
    private ArgumentCaptor<String> actualCode = ArgumentCaptor.forClass(String.class);

    @MockBean
    private PhoneVerificationService phoneVerificationService;
    @MockBean
    private RegularUserService userService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() throws GlobalException {
        Mockito.doNothing().when(phoneVerificationService).verify(actualPhone.capture(), actualCode.capture());
        Mockito.doAnswer(args -> args.getArgument(0)).when(userService).create(Mockito.any(RegularUserEntity.class));
    }

    @Test
    @Order(1)
    public void successRegister() throws Exception {
        final var code = "123";
        final var dto = RUserDtoBuilder.builder()
                .email("test@email.com")
                .firstName("test")
                .lastName("test")
                .password("1234")
                .matchingPassword("1234")
                .phone("+989333938680").build();


        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .param(PARAM_CODE, code)
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", Matchers.is(dto.getFirstName())))
                .andExpect(jsonPath("$.lastName", Matchers.is(dto.getLastName())))
                .andExpect(jsonPath("$.phone", Matchers.is(dto.getPhone())))
                .andExpect(jsonPath("$.email", Matchers.is(dto.getEmail())))
                .andExpect(jsonPath("$.isEnabled", Matchers.is(dto.isEnabled())));
    }

    @Test
    @Order(2)
    public void successWithoutPass() throws Exception {
        final var code = "123";
        final var dto = RUserDtoBuilder.builder()
                .email("test@email.com")
                .firstName("test")
                .lastName("test")
                .phone("+989333938680").build();


        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .param(PARAM_CODE, code)
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", Matchers.is(dto.getFirstName())))
                .andExpect(jsonPath("$.lastName", Matchers.is(dto.getLastName())))
                .andExpect(jsonPath("$.phone", Matchers.is(dto.getPhone())))
                .andExpect(jsonPath("$.email", Matchers.is(dto.getEmail())))
                .andExpect(jsonPath("$.isEnabled", Matchers.is(dto.isEnabled())));
    }

    @Test
    @Order(3)
    public void nullVerificationCode() throws Exception {
        final var dto = RUserDtoBuilder.builder()
                .email("test@email.com")
                .firstName("test")
                .lastName("test")
                .phone("+989333938680").build();


        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.key", Matchers.is(ERROR_VALIDATION)))
                .andExpect(jsonPath("$.messages", Matchers.hasItem(CODE_IS_NULL)));
    }

    @Test
    @Order(4)
    public void emptyVerificationCode() throws Exception {
        Mockito.doThrow(new PhoneVerificationException(CODE_OR_PHONE_IS_NOT_VALID_MSG))
                .when(phoneVerificationService).verify(actualPhone.capture(), actualCode.capture());
        final var code = "";
        final var dto = RUserDtoBuilder.builder()
                .email("test@email.com")
                .firstName("test")
                .lastName("test")
                .phone("+989333938680").build();


        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .param(PARAM_CODE, code)
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.key", Matchers.is(ERROR_PHONE_VERIFICATION)))
                .andExpect(jsonPath("$.messages", Matchers.hasItem(CODE_OR_PHONE_IS_NOT_VALID_MSG)));
    }

    @Test
    @Order(5)
    public void invalidVerificationCode() throws Exception {
        Mockito.doThrow(new PhoneVerificationException(CODE_OR_PHONE_IS_NOT_VALID_MSG))
                .when(phoneVerificationService).verify(actualPhone.capture(), actualCode.capture());
        final var code = "invalid";
        final var dto = RUserDtoBuilder.builder()
                .email("test@email.com")
                .firstName("test")
                .lastName("test")
                .phone("+989333938680").build();


        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .param(PARAM_CODE, code)
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.key", Matchers.is(ERROR_PHONE_VERIFICATION)))
                .andExpect(jsonPath("$.messages", Matchers.hasItem(CODE_OR_PHONE_IS_NOT_VALID_MSG)));
    }

    @Test
    @Order(6)
    public void nullPhone() throws Exception {
        Mockito.doThrow(new PhoneVerificationException(CODE_OR_PHONE_IS_NOT_VALID_MSG))
                .when(phoneVerificationService).verify(actualPhone.capture(), actualCode.capture());
        final var code = "123";
        final var dto = RUserDtoBuilder.builder()
                .email("test@email.com")
                .firstName("test")
                .lastName("test").build();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .param(PARAM_CODE, code)
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.key", Matchers.is(ERROR_PHONE_VERIFICATION)))
                .andExpect(jsonPath("$.messages", Matchers.hasItem(CODE_OR_PHONE_IS_NOT_VALID_MSG)));
    }

    private String asJsonString(Object entity) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(entity);
    }

}
