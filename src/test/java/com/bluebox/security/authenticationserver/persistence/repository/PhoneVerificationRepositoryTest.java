package com.bluebox.security.authenticationserver.persistence.repository;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author by kamran ghiasvand
 */
@SpringBootTest(properties = {"spring.aop.auto=false"})
@ExtendWith({SpringExtension.class, DBUnitExtension.class})
@DataSet(cleanBefore = true)
@DBUnit(schema = "wedding_auth_server_test")
public class PhoneVerificationRepositoryTest {
    @Autowired
    private PhoneVerificationRepository repository;


    @Test
    @Order(1)
    @DataSet("phoneVerifications.yml")
    public void findById() {
        final var actual = repository.findById(1L);
        Assertions.assertNotNull(actual);
        Assertions.assertFalse(actual.isEmpty());
        final var entity = actual.get();
        Assertions.assertEquals("1", entity.getCode());
        Assertions.assertEquals("123456789", entity.getPhoneNumber());
    }

    @Test
    @Order(1)
    @DataSet("phoneVerifications.yml")
    public void findByNullId() {
        //noinspection ConstantConditions
        final var exception = Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> repository.findById(null));
        Assertions.assertNotNull(exception.getCause());
        Assertions.assertEquals(IllegalArgumentException.class, exception.getCause().getClass());
        Assertions.assertEquals("The given id must not be null!", exception.getCause().getMessage());

    }

    @Test
    @Order(1)
    @DataSet("phoneVerifications.yml")
    public void findByNegativeId() {
        final var actual = repository.findById(-1L);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @Order(2)
    @DataSet("phoneVerifications.yml")
    public void findAll() {
        final var actual = repository.findAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());
        final var first = actual.stream().filter(m -> m.getId() == 1).findFirst();
        Assertions.assertNotNull(first);
        Assertions.assertFalse(first.isEmpty());
        Assertions.assertEquals("1", first.get().getCode());
        Assertions.assertEquals("123456789", first.get().getPhoneNumber());

        final var second = actual.stream().filter(m -> m.getId() == 2).findFirst();
        Assertions.assertNotNull(second);
        Assertions.assertFalse(second.isEmpty());
        Assertions.assertEquals("2", second.get().getCode());
        Assertions.assertEquals("987654321", second.get().getPhoneNumber());

    }

    @Test
    @Order(3)
    @DataSet("phoneVerifications.yml")
    public void findByPhoneNumberAndCode() {
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
    @Order(4)
    @DataSet("phoneVerifications.yml")
    public void phoneNotMatch() {
        final var phone = "unknown phone";
        final var code = "1";
        final var actual = repository.findFirstByPhoneNumberAndCode(phone, code);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @Order(5)
    @DataSet("phoneVerifications.yml")
    public void codeNotMatch() {
        final var phone = "123456789";
        final var code = "not exist code";
        final var actual = repository.findFirstByPhoneNumberAndCode(phone, code);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @Order(6)
    @DataSet("phoneVerifications.yml")
    public void phoneAndCodeNotMatch() {
        final var phone = "not exist phone";
        final var code = "not exist code";
        final var actual = repository.findFirstByPhoneNumberAndCode(phone, code);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @Order(7)
    @DataSet("phoneVerifications.yml")
    public void findByCorrectPhone() {
        final var phone = "123456789";

        final var actual = repository.findFirstByPhoneNumber(phone);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(phone, actual.get().getPhoneNumber());
    }

    @Test
    @Order(8)
    @DataSet("phoneVerifications.yml")
    public void findByNullPhone() {
        final var actual = repository.findFirstByPhoneNumber(null);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }
}
