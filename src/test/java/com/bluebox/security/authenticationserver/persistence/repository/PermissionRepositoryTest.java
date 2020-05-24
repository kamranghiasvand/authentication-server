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
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author by kamran ghiasvand
 */
@SpringBootTest(properties = {"spring.aop.auto=false"})
@ExtendWith({SpringExtension.class, DBUnitExtension.class})
@DataSet(cleanBefore = true)
@DBUnit(schema = "wedding_auth_server_test")
public class PermissionRepositoryTest {
    @Autowired
    private PermissionRepository repository;


    @Test
    @Order(1)
    @DataSet("permissions.yml")
    public void findById() {
        final var actual = repository.findById(1L);
        Assertions.assertNotNull(actual);
        Assertions.assertFalse(actual.isEmpty());
        final var entity = actual.get();
        Assertions.assertEquals("POST /api/test", entity.getName());
    }

    @Test
    @Order(2)
    @DataSet("permissions.yml")
    public void findAll() {
        final var actual = repository.findAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());
        final var first = actual.stream().filter(m -> m.getId() == 1).findFirst();
        Assertions.assertNotNull(first);
        Assertions.assertFalse(first.isEmpty());
        Assertions.assertEquals("POST /api/test", first.get().getName());

        final var second = actual.stream().filter(m -> m.getId() == 2).findFirst();
        Assertions.assertNotNull(second);
        Assertions.assertFalse(second.isEmpty());
        Assertions.assertEquals("GET /api/test", second.get().getName());
    }
}
