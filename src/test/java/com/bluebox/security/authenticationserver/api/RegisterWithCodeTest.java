package com.bluebox.security.authenticationserver.api;

import com.bluebox.security.authenticationserver.Builder.RUserDtoBuilder;
import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.common.exception.PhoneVerificationException;
import com.bluebox.security.authenticationserver.common.viewModel.regular.RUserDto;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RegularUserEntity;
import com.bluebox.security.authenticationserver.persistence.service.PhoneVerificationService;
import com.bluebox.security.authenticationserver.persistence.service.RegularUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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

import static com.bluebox.security.authenticationserver.common.Constants.*;
import static com.bluebox.security.authenticationserver.common.PathConstant.REGISTER_WITH_PHONE;
import static com.bluebox.security.authenticationserver.common.PathConstant.REGISTRATION_BASE;
import static java.text.MessageFormat.format;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegisterWithCodeTest {
    private static final String URL = REGISTRATION_BASE + REGISTER_WITH_PHONE;
    private final String PASS_IS_NULL = format(VALIDATION_IS_NULL_MSG, FIELD_REGULAR_USER_PASSWORD);
    private final String CODE_IS_NULL = format(VALIDATION_IS_NULL_MSG, FIELD_VERIFICATION_CODE);
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
        final var dto = getUser();


        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .param(PARAM_CODE, code)
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(dto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(dto.getLastName())))
                .andExpect(jsonPath("$.phone", is(dto.getPhone())))
                .andExpect(jsonPath("$.email", is(dto.getEmail())))
                .andExpect(jsonPath("$.isEnabled", is(true)));
    }

    @Test
    @Order(2)
    public void nullPass() throws Exception {
        final var code = "123";
        final var dto = getUser();
        dto.setPassword(null);
        dto.setMatchingPassword(null);

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .param(PARAM_CODE, code)
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.key", is(ERROR_VALIDATION)))
                .andExpect(jsonPath("$.messages", hasItem(PASS_IS_NULL)));
    }

    private RUserDto getUser() {
        return RUserDtoBuilder.builder()
                .email("test@email.com")
                .firstName("test")
                .lastName("test")
                .password("1234")
                .matchingPassword("1234")
                .phone("+989333938680").build();
    }

    @Test
    @Order(3)
    public void nullVerificationCode() throws Exception {
        final var dto = RUserDtoBuilder.builder()
                .email("test@email.com")
                .firstName("test")
                .lastName("test")
                .password("password")
                .matchingPassword("password")
                .phone("+989333938680").build();


        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.key", is(ERROR_VALIDATION)))
                .andExpect(jsonPath("$.messages", hasItem(CODE_IS_NULL)));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.key", is(ERROR_PHONE_VERIFICATION)))
                .andExpect(jsonPath("$.messages", hasItem(CODE_OR_PHONE_IS_NOT_VALID_MSG)));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.key", is(ERROR_PHONE_VERIFICATION)))
                .andExpect(jsonPath("$.messages", hasItem(CODE_OR_PHONE_IS_NOT_VALID_MSG)));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.key", is(ERROR_PHONE_VERIFICATION)))
                .andExpect(jsonPath("$.messages", hasItem(CODE_OR_PHONE_IS_NOT_VALID_MSG)));
    }

    @Test
    @Order(7)
    public void invalidUserPhone() throws Exception {
        Mockito.doThrow(new PhoneVerificationException(CODE_OR_PHONE_IS_NOT_VALID_MSG))
                .when(phoneVerificationService).verify(actualPhone.capture(), actualCode.capture());
        final var code = "123";
        final var dto = RUserDtoBuilder.builder()
                .email("test@email.com")
                .firstName("test")
                .lastName("test")
                .phone("invalid").build();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .param(PARAM_CODE, code)
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.key", is(ERROR_PHONE_VERIFICATION)))
                .andExpect(jsonPath("$.messages", hasItem(CODE_OR_PHONE_IS_NOT_VALID_MSG)));
    }

    @Test
    @Order(8)
    public void emptyUserPhone() throws Exception {
        Mockito.doThrow(new PhoneVerificationException(CODE_OR_PHONE_IS_NOT_VALID_MSG))
                .when(phoneVerificationService).verify(actualPhone.capture(), actualCode.capture());
        final var code = "123";
        final var dto = RUserDtoBuilder.builder()
                .email("test@email.com")
                .firstName("test")
                .lastName("test")
                .phone("").build();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .param(PARAM_CODE, code)
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.key", is(ERROR_PHONE_VERIFICATION)))
                .andExpect(jsonPath("$.messages", hasItem(CODE_OR_PHONE_IS_NOT_VALID_MSG)));
    }

    @Test
    @Order(9)
    public void successWithFullUserInfo() throws Exception {
        final var code = "123";
        final var dto = RUserDtoBuilder.builder()
                .firstName("test")
                .lastName("test")
                .email("email@gmail.com")
                .password("1234")
                .matchingPassword("1234")
                .phone("+989333938680")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .param(PARAM_CODE, code)
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.phone", is(dto.getPhone())))
                .andExpect(jsonPath("$.firstName", is(dto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(dto.getLastName())))
                .andExpect(jsonPath("$.email", is(dto.getEmail())))
                .andExpect(jsonPath("$.isEnabled", is(true)));
    }

    private String asJsonString(Object entity) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(entity);
    }

}
