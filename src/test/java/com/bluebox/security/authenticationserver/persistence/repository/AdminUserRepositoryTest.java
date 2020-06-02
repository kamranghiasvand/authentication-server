package com.bluebox.security.authenticationserver.persistence.repository;

import com.bluebox.security.authenticationserver.persistence.entity.administrator.AdminUserEntity;
import com.bluebox.security.authenticationserver.security.UserPrincipal;
import com.bluebox.security.authenticationserver.util.CustomDBUnitExtension;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by kamran ghiasvand
 */
@SpringBootTest
@ExtendWith({SpringExtension.class, CustomDBUnitExtension.class})
@DataSet(value = "adminUsers.yml", cleanBefore = true, useSequenceFiltering = false)
@DBUnit(schema = "wedding_auth_server_test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class AdminUserRepositoryTest {
    private final String DOMAIN_NAME = "test app";
    @Autowired
    private AdminUserRepository repository;
    private AdminUserEntity rootUser;
    private AdminUserEntity harryPotter;

    public AdminUserRepositoryTest() throws ParseException {
        createHarryPotter();
        createRoot(harryPotter);
        harryPotter.setParent(rootUser);
    }

    @PostConstruct
    public void postConstruct() {
        UserPrincipal principal = UserPrincipal.newBuilder().setDomain(DOMAIN_NAME).build();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, null));
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
    public void findRootByEmail() {
        final var email = rootUser.getEmail();
        final var actual = repository.findByEmail(email);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(rootUser, actual.get());
    }

    @Test
    @Order(5)
    public void findHarryByEmail() {
        final var email = harryPotter.getEmail();
        final var actual = repository.findByEmail(email);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(harryPotter, actual.get());
    }

    @Test
    @Order(6)
    public void findHarryByEmailInDifferentDomain() {
        UserPrincipal principal = UserPrincipal.newBuilder().setDomain("other_domain").build();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, null));

        final var email = harryPotter.getEmail();
        final var actual = repository.findByEmail(email);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    private void createHarryPotter() throws ParseException {
        harryPotter = new AdminUserEntity();
        harryPotter.setId(2L);
        harryPotter.setFirstName("harry");
        harryPotter.setLastName("potter");
        harryPotter.setParent(null);
        harryPotter.setEmail("harry.potter@email.com");
        harryPotter.setPermissions(new ArrayList<>());
        harryPotter.setDomain(DOMAIN_NAME);
        harryPotter.setEnabled(true);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        harryPotter.setLastUpdateDate(new Timestamp(df.parse("2020-05-02 01:57:47").getTime()));
        harryPotter.setPassword("root");
        harryPotter.setPhone("+123456789012");
        harryPotter.setRegistrationDate(new Timestamp(df.parse("2020-05-02 01:57:47").getTime()));
    }

    private void createRoot(AdminUserEntity... children) throws ParseException {
        rootUser = new AdminUserEntity();
        rootUser.setId(1L);
        rootUser.setFirstName("root");
        rootUser.setLastName("superman");
        rootUser.setParent(null);
        rootUser.setEmail("root@email.com");
        rootUser.setPermissions(new ArrayList<>());
        rootUser.setChildren(List.of(children));
        rootUser.setDomain(DOMAIN_NAME);
        rootUser.setEnabled(true);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        rootUser.setLastUpdateDate(new Timestamp(df.parse("2020-05-02 01:57:47").getTime()));
        rootUser.setPassword("root");
        rootUser.setPhone("+123456789012");
        rootUser.setRegistrationDate(new Timestamp(df.parse("2020-05-02 01:57:47").getTime()));
    }

}
