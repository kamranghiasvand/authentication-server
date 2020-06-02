package com.bluebox.security.authenticationserver.persistence.repository.phoneVerification;

import com.bluebox.security.authenticationserver.persistence.repository.PhoneVerificationRepository;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author by kamran ghiasvand
 */
@SpringBootTest(properties = {"spring.aop.auto=false"})
@ExtendWith({SpringExtension.class, DBUnitExtension.class})
@DataSet(value = "phoneVerifications.yml", cleanBefore = true)
@DBUnit(schema = "wedding_auth_server_test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class FindByPhoneAndCodeTest {
    @Autowired
    private PhoneVerificationRepository repository;

    @Test
    @Order(1)
    public void successFind() {
        final var phone = "123456789";
        final var code = "1";
        final var actual = repository.findFirstByPhoneNumberAndCode(phone, code);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(phone, actual.get().getPhoneNumber());
        Assertions.assertEquals(code, actual.get().getCode());
        Assertions.assertEquals(1, actual.get().getId());
    }

    @Test
    @Order(2)
    public void findByNullPhone() {
        final var code = "1";
        final var actual = repository.findFirstByPhoneNumberAndCode(null, code);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @Order(3)
    public void findByEmptyPhone() {
        final var phone = "";
        final var code = "1";
        final var actual = repository.findFirstByPhoneNumberAndCode(phone, code);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @Order(4)
    public void findByNullCode() {
        final var phone = "123456789";
        final var actual = repository.findFirstByPhoneNumberAndCode(phone, null);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @Order(5)
    public void findByEmptyCode() {
        final var phone = "123456789";
        final String code = "";
        final var actual = repository.findFirstByPhoneNumberAndCode(phone, code);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }


    @Test
    @Order(6)
    public void phoneNotMatch() {
        final var phone = "unknown phone";
        final var code = "1";
        final var actual = repository.findFirstByPhoneNumberAndCode(phone, code);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @Order(7)
    public void codeNotMatch() {
        final var phone = "123456789";
        final var code = "not exist code";
        final var actual = repository.findFirstByPhoneNumberAndCode(phone, code);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @Order(8)
    public void phoneAndCodeNotMatch() {
        final var phone = "not exist phone";
        final var code = "not exist code";
        final var actual = repository.findFirstByPhoneNumberAndCode(phone, code);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }
}
