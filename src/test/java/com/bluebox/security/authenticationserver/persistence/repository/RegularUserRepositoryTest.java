package com.bluebox.security.authenticationserver.persistence.repository;

import com.bluebox.security.authenticationserver.persistence.entity.regular.RegularUserEntity;
import com.bluebox.security.authenticationserver.security.UserPrincipal;
import com.bluebox.security.authenticationserver.util.CustomDBUnitExtension;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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

/**
 * @author by kamran ghiasvand
 */
@SpringBootTest
@ExtendWith({SpringExtension.class, CustomDBUnitExtension.class})
@DataSet(value = "regularUsers.yml", cleanBefore = true, useSequenceFiltering = false)
@DBUnit(schema = "wedding_auth_server_test")
@Transactional
public class RegularUserRepositoryTest {
    private final String DOMAIN_NAME = "test app";
    @Autowired
    private RegularUserRepository repository;
    private RegularUserEntity hermione;
    private RegularUserEntity harryPotter;

    public RegularUserRepositoryTest() throws ParseException {
        createHermione();
        createHarryPotter();
    }

    @PostConstruct
    public void postConstruct() {
        UserPrincipal principal = UserPrincipal.newBuilder().setDomain(DOMAIN_NAME).build();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, null));
    }


    @Test
    @Order(1)
    public void findByNullPhone() {
        final var actual = repository.findByPhone(null);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @Order(2)
    public void findByEmptyPhone() {
        final var phone = "";
        final var actual = repository.findByPhone(phone);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @Order(3)
    public void findByNotExistPhone() {
        final var phone = "non exist phone";
        final var actual = repository.findByPhone(phone);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @Order(4)
    public void findRootByPhone() {
        final var phone = hermione.getPhone();
        final var actual = repository.findByPhone(phone);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(hermione, actual.get());
    }

    @Test
    @Order(5)
    public void findHarryByPhone() {
        final var phone = harryPotter.getPhone();
        final var actual = repository.findByPhone(phone);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(harryPotter, actual.get());
    }

    @Test
    @Order(6)
    public void findHarryByPhoneInDifferentDomain() {
        UserPrincipal principal = UserPrincipal.newBuilder().setDomain("other_domain").build();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, null));

        final var phone = harryPotter.getPhone();
        final var actual = repository.findByPhone(phone);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    private void createHarryPotter() throws ParseException {
        harryPotter = new RegularUserEntity();
        harryPotter.setId(2L);
        harryPotter.setFirstName("harry");
        harryPotter.setLastName("potter");
        harryPotter.setEmail("harry.potter@email.com");
        harryPotter.setDomain(DOMAIN_NAME);
        harryPotter.setEnabled(true);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        harryPotter.setLastUpdateDate(new Timestamp(df.parse("2020-05-02 01:57:47").getTime()));
        harryPotter.setPassword("root");
        harryPotter.setPhone("+123456789013");
        harryPotter.setRegistrationDate(new Timestamp(df.parse("2020-05-02 01:57:47").getTime()));
    }

    private void createHermione() throws ParseException {
        hermione = new RegularUserEntity();
        hermione.setId(1L);
        hermione.setDomain(DOMAIN_NAME);
        hermione.setFirstName("hermione");
        hermione.setLastName("granger");
        hermione.setEmail("hermione.granger@email.com");
        hermione.setEnabled(true);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        hermione.setLastUpdateDate(new Timestamp(df.parse("2020-05-02 01:57:47").getTime()));
        hermione.setPassword("root");
        hermione.setPhone("+123456789012");
        hermione.setRegistrationDate(new Timestamp(df.parse("2020-05-02 01:57:47").getTime()));
    }

}
