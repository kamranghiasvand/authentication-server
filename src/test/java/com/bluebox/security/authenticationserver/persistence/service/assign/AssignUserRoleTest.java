package com.bluebox.security.authenticationserver.persistence.service.assign;

import com.bluebox.security.authenticationserver.Builder.RegularUserEntityBuilder;
import com.bluebox.security.authenticationserver.Builder.RoleEntityBuilder;
import com.bluebox.security.authenticationserver.common.exception.ResourceNotFoundException;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RegularUserEntity;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RoleEntity;
import com.bluebox.security.authenticationserver.persistence.repository.RegularUserRepository;
import com.bluebox.security.authenticationserver.persistence.repository.RoleRepository;
import com.bluebox.security.authenticationserver.persistence.service.AssignService;
import com.bluebox.security.authenticationserver.security.UserPrincipal;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;

import static com.bluebox.security.authenticationserver.common.Constants.*;
import static java.text.MessageFormat.format;

/**
 * @author Kamran Ghiasvand
 */
@SpringBootTest
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class AssignUserRoleTest {
    private final String DOMAIN_NAME = "test app";
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RegularUserRepository userRepository;
    @Autowired
    private AssignService assignService;
    private boolean init = false;
    private RegularUserEntity user;
    private RoleEntity role;

    @PostConstruct
    public void postConstruct() {
        UserPrincipal principal = UserPrincipal.newBuilder().setDomain(DOMAIN_NAME).build();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, null));
    }

    @BeforeEach
    public void beforeEach() {
        if (init)
            return;
        init = true;
        userRepository.deleteAll();
        roleRepository.deleteAll();
        user = RegularUserEntityBuilder.newBuilder().firstName("harry").lastName("jonson").domain(DOMAIN_NAME)
                .enabled(true).password("password").phone("+989112342323").email("harry.jonson@gmail.com")
                .updateTime(new Timestamp(System.currentTimeMillis()))
                .registrationDate(new Timestamp(System.currentTimeMillis()))
                .build();
        user = userRepository.save(user);
        role = RoleEntityBuilder.newBuilder().domain(DOMAIN_NAME).name("ROLE-1").build();
        role = roleRepository.save(role);
    }


    @Test
    @Order(1)
    public void nullRuleToNullUser() {
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(null, null));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, "null"), exception.getMessages().get(0));
    }

    @Test
    @Order(2)
    public void negativeRuleIdToNullUser() {
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(null, -1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, "null"), exception.getMessages().get(0));
    }

    @Test
    @Order(3)
    public void zeroRuleIdToNullUser() {
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(null, 0L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, "null"), exception.getMessages().get(0));
    }

    @Test
    @Order(4)
    public void positiveRuleIdToNullUser() {
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(null, 1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, "null"), exception.getMessages().get(0));
    }

    @Test
    @Order(5)
    public void nullRuleToNegativeUserId() {
        final var userId = -1L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, null));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(6)
    public void negativeRuleIdToNegativeUserId() {
        final var userId = -1L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, -1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(7)
    public void zeroRuleIdToNegativeUserId() {
        final var userId = -1L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, 0L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(8)
    public void positiveRuleIdToNegativeUserId() {
        final var userId = -1L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, 1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(9)
    public void nullRuleToZeroUserId() {
        final var userId = 0L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, null));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(10)
    public void negativeRuleIdToZeroUserId() {
        final var userId = 0L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, -1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(11)
    public void zeroRuleIdToZeroUserId() {
        final var userId = 0L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, 0L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(12)
    public void positiveRuleIdToZeroUserId() {
        final var userId = 0L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, 1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }


    @Test
    @Order(13)
    public void nullRuleToPositiveNotExistUserId() {
        final var userId = 1000L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, null));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(14)
    public void negativeRuleIdToPositiveNotExistUserId() {
        final var userId = 1000L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, -1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(15)
    public void zeroRuleIdToPositiveNotExistUserId() {
        final var userId = 1000L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, 0L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(16)
    public void positiveRuleIdToPositiveNotExistUserId() {
        final var userId = 1000L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, 1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, USER, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(13)
    public void nullRuleToUser() {
        final var userId = user.getId();
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, null));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, "null"), exception.getMessages().get(0));
    }

    @Test
    @Order(14)
    public void negativeRuleIdToUser() {
        final var userId = user.getId();
        final var roleId = -1L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, roleId));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, roleId), exception.getMessages().get(0));
    }

    @Test
    @Order(15)
    public void zeroRuleIdToUser() {
        final var userId = user.getId();
        final var roleId = 0L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, roleId));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, roleId), exception.getMessages().get(0));
    }

    @Test
    @Order(16)
    public void positiveNotExistRuleIdToUser() {
        final var userId = user.getId();
        final var roleId = 1000L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignUserRole(userId, roleId));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, roleId), exception.getMessages().get(0));
    }

    @Test
    @Order(16)
    public void ruleIdToUser() throws ResourceNotFoundException {
        assignService.assignUserRole(user.getId(), role.getId());
        final var resp = userRepository.findById(user.getId());
        Assertions.assertNotNull(resp);
        Assertions.assertTrue(resp.isPresent());
        final var actual = resp.get();
        Assertions.assertNotNull(actual.getRoles());
        Assertions.assertEquals(1, actual.getRoles().size());
        final var actualRole = actual.getRoles().get(0);
        Assertions.assertEquals(role, actualRole);
    }

}
