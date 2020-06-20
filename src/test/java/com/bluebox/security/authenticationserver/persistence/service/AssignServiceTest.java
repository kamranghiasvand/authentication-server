package com.bluebox.security.authenticationserver.persistence.service;

import com.bluebox.security.authenticationserver.Builder.RegularUserEntityBuilder;
import com.bluebox.security.authenticationserver.common.exception.ResourceNotFoundException;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RegularUserEntity;
import com.bluebox.security.authenticationserver.persistence.repository.PermissionRepository;
import com.bluebox.security.authenticationserver.persistence.repository.RegularUserRepository;
import com.bluebox.security.authenticationserver.persistence.repository.RoleRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

import static com.bluebox.security.authenticationserver.common.Constants.USER;
import static com.bluebox.security.authenticationserver.common.Constants.VALIDATION_NOT_FOUND_MSG;
import static java.text.MessageFormat.format;

/**
 * @author Kamran Ghiasvand
 */
@SpringBootTest
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class AssignServiceTest {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RegularUserRepository userRepository;
    @Autowired
    private AssignService assignService;
    private boolean init = false;
    private RegularUserEntity user;

    @BeforeEach
    public void beforeEach() {
        if (init)
            return;
        init = true;
        userRepository.deleteAll();
        roleRepository.deleteAll();
        user = RegularUserEntityBuilder.newBuilder().firstName("harry").lastName("jonson").domain("test app")
                .enabled(true).password("password").phone("+989112342323").email("harry.jonson@gmail.com")
                .updateTime(new Timestamp(System.currentTimeMillis()))
                .registrationDate(new Timestamp(System.currentTimeMillis()))
                .build();
        user = userRepository.save(user);
    }

    @Test
    @Order(1)
    public void assignNullRuleToNullUser() {
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(null, null));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, "null"), exception.getMessages().get(0));
    }

    @Test
    @Order(2)
    public void assignNegativeRuleIdToNullUser() {
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(null, -1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, "null"), exception.getMessages().get(0));
    }

    @Test
    @Order(3)
    public void assignZeroRuleIdToNullUser() {
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(null, 0L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, "null"), exception.getMessages().get(0));
    }

    @Test
    @Order(4)
    public void assignPositiveRuleIdToNullUser() {
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(null, 1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, "null"), exception.getMessages().get(0));
    }

    @Test
    @Order(5)
    public void assignNullRuleToNegativeUserId() {
        final var userId = -1L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, null));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(6)
    public void assignNegativeRuleIdToNegativeUserId() {
        final var userId = -1L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, userId));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(7)
    public void assignZeroRuleIdToNegativeUserId() {
        final var userId = -1L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, 0L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(8)
    public void assignPositiveRuleIdToNegativeUserId() {
        final var userId = -1L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, 1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(9)
    public void assignNullRuleToZeroUserId() {
        final var userId = 0L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, null));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(10)
    public void assignNegativeRuleIdToZeroUserId() {
        final var userId = 0L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, userId));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(11)
    public void assignZeroRuleIdToZeroUserId() {
        final var userId = 0L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, 0L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(12)
    public void assignPositiveRuleIdToZeroUserId() {
        final var userId = 0L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, 1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

}
