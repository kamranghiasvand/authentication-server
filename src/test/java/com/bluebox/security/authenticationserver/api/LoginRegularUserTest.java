package com.bluebox.security.authenticationserver.api;

import com.bluebox.security.authenticationserver.Builder.RegularUserEntityBuilder;
import com.bluebox.security.authenticationserver.persistence.repository.PermissionRepository;
import com.bluebox.security.authenticationserver.persistence.repository.RegularUserRepository;
import com.bluebox.security.authenticationserver.persistence.repository.RoleRepository;
import com.bluebox.security.authenticationserver.persistence.service.AssignService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Kamran Ghiasvand
 */

@SpringBootTest(properties = {"spring.aop.auto=false"})
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
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
    @Autowired
    private RegularUserRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AssignService assignService;
    @MockBean
    private ClientDetailsService clientDetailsService;

    public LoginRegularUserTest() {
        client = new BaseClientDetails(CLIENT_ID, CLIENT_RESOURCE_ID, CLIENT_SCOPES, CLIENT_GRANT_TYPE, CLIENT_ROLES);
        client.setClientSecret(CLIENT_SECRET);
    }


    @BeforeEach
    public void before() {
        Mockito.when(clientDetailsService.loadClientByClientId(anyString())).thenAnswer(args -> client);
        userRepository.deleteAll();
//        permissionRepository.deleteAll();

    }

    @Test
    public void testMockIsWorking() {
        var actual = clientDetailsService.loadClientByClientId("");
        Assertions.assertEquals(client, actual);
    }

    @Test
    @Order(1)
    public void test() throws Exception {
        var rBuilder = RegularUserEntityBuilder.newBuilder();
        var user = rBuilder.email("test@test")
                .phone("+989333938680")
                .password("123")
                .enabled(true)
                .domain("app")
                .lastName("test")
                .firstName("test")
                .build();
        user = userRepository.save(user);
        mockMvc.perform(post(URL)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity("username", user.getPhone(), "password", user.getPassword(), "grant_type", "password")))
                .andExpect(status().isOk());
    }

    private String asJsonString(Object entity) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(entity);
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
