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
public class FindByPhoneTest {
    @Autowired
    private PhoneVerificationRepository repository;

    @Test
    @Order(1)
    public void findByCorrectPhone() {
        final var phone = "123456789";
        final var actual = repository.findFirstByPhoneNumber(phone);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(phone, actual.get().getPhoneNumber());
    }

    @Test
    @Order(2)
    public void findByNullPhone() {
        final var actual = repository.findFirstByPhoneNumber(null);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @Order(3)
    public void findByEmptyPhone() {
        final var phone = "";
        final var actual = repository.findFirstByPhoneNumber(phone);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @Order(4)
    public void findByInvalidPhone() {
        final var phone = "invalid Phone";
        final var actual = repository.findFirstByPhoneNumber(phone);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }
}
