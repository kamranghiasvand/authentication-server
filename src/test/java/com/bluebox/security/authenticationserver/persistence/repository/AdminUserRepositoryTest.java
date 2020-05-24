package com.bluebox.security.authenticationserver.persistence.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author by kamran ghiasvand
 */

@SpringBootTest(properties = {"spring.aop.auto=false"})
@ExtendWith({SpringExtension.class, DBUnitExtension.class})
public class AdminUserRepositoryTest {
    @Test
    @DataSet("adminUser.yml")
    public void loadData() {

    }
}
