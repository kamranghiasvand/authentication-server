package com.bluebox.security.authenticationserver.persistence;

import com.bluebox.security.authenticationserver.Builder.RegularUserEntityBuilder;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RegularUserEntity;
import com.bluebox.security.authenticationserver.persistence.repository.RegularUserRepository;
import com.bluebox.security.authenticationserver.persistence.service.RegularUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * @author Kamran Ghiasvand
 */
@SpringBootTest()
@ExtendWith({SpringExtension.class})
public class RegularUserServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegularUserServiceTest.class);
    @Autowired
    private RegularUserService service;
    @Autowired
    private RegularUserRepository repository;

    @BeforeEach
    public void before() {
        repository.deleteAll();
    }

    @Test
    @Order(1)
    public void successCreateUser() {
        var expected = buildUser();
        successCreateUserAndAssert(expected);
    }

    @Test
    @Order(2)
    public void successCreateUserWithoutEmail() {
        var expected = buildUser();
        expected.setEmail(null);
        successCreateUserAndAssert(expected);
    }

    @Test
    @Order(3)
    public void successCreateUserWithoutFirstName() {
        var expected = buildUser();
        expected.setFirstName(null);
        successCreateUserAndAssert(expected);
    }

    @Test
    @Order(4)
    public void successCreateUserWithoutLastName() {
        var expected = buildUser();
        expected.setLastName(null);
        successCreateUserAndAssert(expected);
    }

    @Test
    @Order(5)
    public void successCreateUserWithoutRegistrationDate() {
        var expected = buildUser();
        expected.setRegistrationDate(null);
        successCreateUserAndAssert(expected);
    }

    @Test
    @Order(6)
    public void successCreateUserWithoutRoles() {
        var expected = buildUser();
        expected.setRoles(null);
        successCreateUserAndAssert(expected);
    }

    @Test
    @Order(7)
    public void successCreateUserWithoutLastUpdate() {
        var expected = buildUser();
        expected.setLastUpdateDate(null);
        successCreateUserAndAssert(expected);
    }

    @Test
    @Order(8)
    public void emptyDomain() {
        var expected = buildUser();
        expected.setDomain(null);
        var exception = Assertions.assertThrows(DataIntegrityViolationException.class, () -> service.create(expected));
        Assertions.assertNotNull(exception.getRootCause());
        Assertions.assertEquals("Column 'domain' cannot be null", exception.getRootCause().getMessage());
    }

    @Test
    @Order(9)
    public void emptyPassword() {
        var expected = buildUser();
        expected.setPassword(null);
        var exception = Assertions.assertThrows(DataIntegrityViolationException.class, () -> service.create(expected));
        Assertions.assertNotNull(exception.getRootCause());
        Assertions.assertEquals("Column 'password' cannot be null", exception.getRootCause().getMessage());
    }


    private void successCreateUserAndAssert(RegularUserEntity expected) {
        var inDb = createUser(expected);
        Assertions.assertFalse(inDb.isEmpty());
        var actual = inDb.get();
        assertUser(expected, actual);
    }


    private Optional<RegularUserEntity> createUser(RegularUserEntity expected) {
        var resp = service.create(expected);
        return repository.findById(resp.getId());
    }


    private RegularUserEntity buildUser() {
        var builder = RegularUserEntityBuilder.newBuilder();
        return builder.domain("domain")
                .email("test@test.com")
                .enabled(false)
                .firstName("test")
                .lastName("test")
                .password("123")
                .phone("9123333333")
                .updateTime(new Timestamp(System.currentTimeMillis()))
                .registrationDate(new Timestamp(System.currentTimeMillis()))
                .build();
    }


    private void assertUser(RegularUserEntity expected, RegularUserEntity actual) {
        LOGGER.info("expected:" + expected.toString());
        LOGGER.info("actual:" + actual.toString());
        Assertions.assertEquals(expected.getDomain(), actual.getDomain());
        Assertions.assertEquals(expected.getEmail(), actual.getEmail());
        Assertions.assertEquals(expected.getPhone(), actual.getPhone());
        Assertions.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assertions.assertEquals(expected.getLastName(), actual.getLastName());
        Assertions.assertNotNull(actual.getLastUpdateDate());
        Assertions.assertNotNull(actual.getRegistrationDate());
    }

}
