package com.bluebox.security.authenticationserver.persistence.repository;

import com.bluebox.security.authenticationserver.persistence.entity.administrator.AdminUserEntity;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.DataSetExecutor;
import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

/**
 * @author by kamran ghiasvand
 */
@SpringBootTest(properties = {"spring.aop.auto=false"})
@ExtendWith({SpringExtension.class, DBUnitExtension.class})
@DataSet(value = "adminUsers.xml", cleanBefore = true)
@DBUnit(schema = "wedding_auth_server_test")
public class AdminUserRepositoryTest {
    @Autowired
    private AdminUserRepository repository;


    @Test
    @Order(1)
    public void findByNullEmail() {
        final var actual = repository.findByEmail(null);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }
}
