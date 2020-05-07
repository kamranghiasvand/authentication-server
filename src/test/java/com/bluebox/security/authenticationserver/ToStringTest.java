package com.bluebox.security.authenticationserver;

import com.bluebox.security.authenticationserver.Builder.PermissionEntityBuilder;
import com.bluebox.security.authenticationserver.Builder.RegularUserEntityBuilder;
import com.bluebox.security.authenticationserver.Builder.RoleEntityBuilder;
import com.bluebox.security.authenticationserver.api.controller.assign.AssignController;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import java.sql.Timestamp;

/**
 * @author by kamran ghiasvand
 */
public class ToStringTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToStringTest.class);

    @Test
    public void test() {
        final var permission = PermissionEntityBuilder.newBuilder()
                .id(1L)
                .domain("domain")
                .method(HttpMethod.POST)
                .name("per name")
                .url("/api/test").build();
        final var role = RoleEntityBuilder.newBuilder()
                .id(1L)
                .domain("domain")
                .name("role name")
                .permission(permission)
                .build();
        permission.getRoles().add(role);
        final var user = RegularUserEntityBuilder.newBuilder()
                .id(1L)
                .domain("domain")
                .email("test@test")
                .enabled(false)
                .firstName("firstName")
                .lastName("lastName")
                .password("password")
                .phone("phone")
                .registrationDate(new Timestamp(System.currentTimeMillis()))
                .updateTime(new Timestamp(System.currentTimeMillis()))
                .roles(role)
                .build();
        role.getUsers().add(user);
        LOGGER.info(user.toString());

    }
}
