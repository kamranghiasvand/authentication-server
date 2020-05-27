package com.bluebox.security.authenticationserver.persistence.repository;

import com.bluebox.security.authenticationserver.persistence.entity.administrator.AdminUserEntity;
import com.bluebox.security.authenticationserver.util.CustomDBUnitExtension;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by kamran ghiasvand
 */
@SpringBootTest(properties = {"spring.aop.auto=false"})
@ExtendWith({SpringExtension.class, CustomDBUnitExtension.class})
@DataSet(value = "adminUsers.yml", cleanBefore = true, useSequenceFiltering = false)
@DBUnit(schema = "wedding_auth_server_test")
@Transactional
public class AdminUserRepositoryTest {
    @Autowired
    private AdminUserRepository repository;
    private final AdminUserEntity rootUser;
    private final AdminUserEntity harryPotter;

    public AdminUserRepositoryTest() throws ParseException {
        harryPotter = new AdminUserEntity();
        //harryPotter.set

        rootUser = new AdminUserEntity();
        rootUser.setId(1L);
        rootUser.setFirstName("root");
        rootUser.setLastName("superman");
        rootUser.setParent(null);
        rootUser.setEmail("root@email.com");
        rootUser.setPermissions(new ArrayList<>());
        rootUser.setChildren(List.of(harryPotter));
        rootUser.setDomain("test app");
        rootUser.setEnabled(true);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        rootUser.setLastUpdateDate(new Timestamp(df.parse("2020-05-02 01:57:47").getTime()));
        rootUser.setPassword("root");
        rootUser.setPhone("+123456789012");
        rootUser.setRegistrationDate(new Timestamp(df.parse("2020-05-02 01:57:47").getTime()));
    }

    @Test
    @Order(1)
    public void findByNullEmail() {
        final var actual = repository.findByEmail(null);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @Order(2)
    public void findByEmptyEmail() {
        final var email = "";
        final var actual = repository.findByEmail(email);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @Order(3)
    public void findByNotExistEmail() {
        final var email = "not.exist@email.com";
        final var actual = repository.findByEmail(email);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @Order(4)
    public void findByExistEmail() {
        final var email = "root@email.com";
        final var actual = repository.findByEmail(email);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(rootUser, actual.get());
    }
}
