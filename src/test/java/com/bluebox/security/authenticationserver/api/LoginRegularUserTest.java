package com.bluebox.security.authenticationserver.api;

import com.bluebox.security.authenticationserver.Builder.RegularUserEntityBuilder;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RegularUserEntity;
import com.bluebox.security.authenticationserver.persistence.repository.RegularUserRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Kamran Ghiasvand
 */

@SpringBootTest(properties = {"spring.aop.auto=false", "app.production-mode=false"})
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginRegularUserTest {
    private static final String URL = "/oauth/token";
    private final String CLIENT_ID = "app";
    private final String CLIENT_SECRET = "123";
    private final String CLIENT_RESOURCE_ID = "resource_id";
    private final String CLIENT_SCOPES = "READ,WRITE";
    private final String CLIENT_ROLES = "ROLE_GUEST_APP";
    private final String CLIENT_GRANT_TYPE = "client_credentials,password,authorization_code,refresh_token,implicit";
    private final BaseClientDetails client;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RegularUserRepository userRepository;

    @MockBean
    private ClientDetailsService clientDetailsService;

    public LoginRegularUserTest() {
        client = new BaseClientDetails(CLIENT_ID, CLIENT_RESOURCE_ID, CLIENT_SCOPES, CLIENT_GRANT_TYPE, CLIENT_ROLES);
        client.setClientSecret(CLIENT_SECRET);
    }


    @BeforeEach
    public void before() {
        Mockito.when(clientDetailsService.loadClientByClientId(anyString())).thenAnswer(args -> client);

    }

    @Test
    @Order(1)
    public void successfulLogin() throws Exception {
        var rBuilder = RegularUserEntityBuilder.newBuilder();
        var user = rBuilder.email("test@test")
                .id(1L)
                .phone("+989333938680")
                .password("123")
                .enabled(true)
                .domain("app")
                .lastName("test")
                .firstName("test")
                .build();
        Mockito.when(userRepository.findByPhone(anyString())).thenReturn(Optional.of(user));
        loginWithUser(user);
    }

    @Test
    @Order(2)
    public void invalidUsername() throws Exception {
        Mockito.when(userRepository.findByPhone(anyString())).thenReturn(Optional.empty());
        mockMvc.perform(post(URL)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity("username", "invalidUser", "password", "password", "grant_type", "password")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid_grant")))
                .andExpect(jsonPath("$.error_description", is("Bad credentials")));
    }

    @Test
    @Order(3)
    public void invalidPassword() throws Exception {
        final var rBuilder = RegularUserEntityBuilder.newBuilder();
        final var user = rBuilder.email("test@test")
                .id(1L)
                .phone("+989333938680")
                .password("123")
                .enabled(true)
                .domain("app")
                .lastName("test")
                .firstName("test")
                .build();
        Mockito.when(userRepository.findByPhone(anyString())).thenReturn(Optional.of(user));
        mockMvc.perform(post(URL)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity("username", "+989333938680", "password", "invalidPassword", "grant_type", "password")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid_grant")))
                .andExpect(jsonPath("$.error_description", is("Bad credentials")));
    }

    @Test
    @Order(4)
    public void invalidClient() throws Exception {
        final var rBuilder = RegularUserEntityBuilder.newBuilder();
        final var user = rBuilder.email("test@test")
                .id(1L)
                .phone("+989333938680")
                .password("123")
                .enabled(true)
                .domain("app")
                .lastName("test")
                .firstName("test")
                .build();
        Mockito.when(userRepository.findByPhone(anyString())).thenReturn(Optional.of(user));
        mockMvc.perform(post(URL)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString(("BAD_CLIENT:BAD_SECRET").getBytes()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity("username", user.getPhone(), "password", user.getPassword(), "grant_type", "password")))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(emptyString()));
    }

    @Test
    @Order(5)
    public void nullClient() throws Exception {
        final var rBuilder = RegularUserEntityBuilder.newBuilder();
        final var user = rBuilder.email("test@test")
                .id(1L)
                .phone("+989333938680")
                .password("123")
                .enabled(true)
                .domain("app")
                .lastName("test")
                .firstName("test")
                .build();
        Mockito.when(userRepository.findByPhone(anyString())).thenReturn(Optional.of(user));
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity("username", user.getPhone(), "password", user.getPassword(), "grant_type", "password")))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(emptyString()));
    }

    @Test
    @Order(6)
    public void loginTwiceReturnSameToken() throws Exception {
        var rBuilder = RegularUserEntityBuilder.newBuilder();
        var user = rBuilder.email("test@test")
                .id(1L)
                .phone("+989333938680")
                .password("123")
                .enabled(true)
                .domain("app")
                .lastName("test")
                .firstName("test")
                .build();
        Mockito.when(userRepository.findByPhone(anyString())).thenReturn(Optional.of(user));

        var result = loginWithUser(user);
        final String firstAccessToken = getAccessToken(result);
        Assertions.assertNotNull(firstAccessToken);

        result = loginWithUser(user);
        final var secondAccessToken = getAccessToken(result);
        Assertions.assertNotNull(secondAccessToken);

        Assertions.assertEquals(firstAccessToken, secondAccessToken);

    }

    private String getAccessToken(ResultActions result) throws UnsupportedEncodingException {
        var content = result.andReturn().getResponse().getContentAsString();
        return getAccessTokenFromContent(content);
    }

    private ResultActions loginWithUser(RegularUserEntity user) throws Exception {
        return mockMvc.perform(post(URL)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity("username", user.getPhone(), "password", user.getPassword(), "grant_type", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token", notNullValue()))
                .andExpect(jsonPath("$.token_type", is("bearer")))
                .andExpect(jsonPath("$.refresh_token", notNullValue()))
                .andExpect(jsonPath("$.scope", equalToIgnoringCase(CLIENT_SCOPES.replace(',', ' '))));
    }

    private String getAccessTokenFromContent(String content) {

        final var jsonPath = JsonPath.compile("$.access_token");
        return jsonPath.read(content);
    }

    private String buildUrlEncodedFormEntity(String... params) {
        if ((params.length % 2) > 0) {
            throw new IllegalArgumentException("Need to give an even number of parameters");
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < params.length; i += 2) {
            if (i > 0) {
                result.append('&');
            }
            try {
                result.
                        append(URLEncoder.encode(params[i], StandardCharsets.UTF_8.name())).
                        append('=').
                        append(URLEncoder.encode(params[i + 1], StandardCharsets.UTF_8.name()));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return result.toString();
    }
}
