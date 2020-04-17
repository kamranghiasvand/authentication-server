package com.bluebox.planner.auth.rest;

import com.bluebox.planner.auth.persistence.service.PhoneVerificationService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.bluebox.planner.auth.common.PathConstant.REGISTRATION_BASE;
import static com.bluebox.planner.auth.common.PathConstant.SEND_PHONE_VERIFICATION_CODE;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class RegistrationControllerTest {
    private static final String V_CODE_URL = REGISTRATION_BASE + SEND_PHONE_VERIFICATION_CODE;
    @MockBean
    private PhoneVerificationService phoneVerificationService;


    @Test
    @Order(1)
    @DisplayName("GET " + V_CODE_URL)
    public void test() {

    }


}
