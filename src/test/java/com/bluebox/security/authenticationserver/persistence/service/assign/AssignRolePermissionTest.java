package com.bluebox.security.authenticationserver.persistence.service.assign;

import com.bluebox.security.authenticationserver.Builder.PermissionEntityBuilder;
import com.bluebox.security.authenticationserver.Builder.RoleEntityBuilder;
import com.bluebox.security.authenticationserver.common.exception.ResourceNotFoundException;
import com.bluebox.security.authenticationserver.persistence.entity.PermissionEntity;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RoleEntity;
import com.bluebox.security.authenticationserver.persistence.repository.PermissionRepository;
import com.bluebox.security.authenticationserver.persistence.repository.RoleRepository;
import com.bluebox.security.authenticationserver.persistence.service.AssignService;
import com.bluebox.security.authenticationserver.security.UserPrincipal;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static com.bluebox.security.authenticationserver.common.Constants.*;
import static java.text.MessageFormat.format;

/**
 * @author Kamran Ghiasvand
 */
@SpringBootTest
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class AssignRolePermissionTest {
    private final String DOMAIN_NAME = "test app";
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private AssignService assignService;
    private boolean init = false;
    private PermissionEntity permission;
    private RoleEntity role;

    @PostConstruct
    public void postConstruct() {
        UserPrincipal principal = UserPrincipal.newBuilder().setDomain(DOMAIN_NAME).build();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, null));
    }

    @BeforeEach
    public void beforeEach() {
        if (init)
            return;
        init = true;
        permissionRepository.deleteAll();
        roleRepository.deleteAll();
        permission = PermissionEntityBuilder.newBuilder().domain(DOMAIN_NAME).method(HttpMethod.GET).name("test per")
                .url("/test-url/").build();
        permission = permissionRepository.save(permission);
        role = RoleEntityBuilder.newBuilder().domain(DOMAIN_NAME).name("ROLE-1").build();
        role = roleRepository.save(role);
    }


    @Test
    @Order(1)
    public void nullPermissionToNullRole() {
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(null, null));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, "null"), exception.getMessages().get(0));
    }

    @Test
    @Order(2)
    public void negativePermissionToNullRole() {
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(null, -1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, "null"), exception.getMessages().get(0));
    }

    @Test
    @Order(3)
    public void zeroPermissionToNullRole() {
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(null, 0L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, "null"), exception.getMessages().get(0));
    }

    @Test
    @Order(4)
    public void positivePermissionToNullRole() {
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(null, 1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, "null"), exception.getMessages().get(0));
    }

    @Test
    @Order(5)
    public void nullPermissionToNegativeRole() {
        final var roleId = -1L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(roleId, null));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, roleId), exception.getMessages().get(0));
    }

    @Test
    @Order(6)
    public void negativePermissionToNegativeRole() {
        final var roleId = -1L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(roleId, -1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, roleId), exception.getMessages().get(0));
    }

    @Test
    @Order(7)
    public void zeroPermissionToNegativeRole() {
        final var roleId = -1L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(roleId, 0L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, roleId), exception.getMessages().get(0));
    }

    @Test
    @Order(8)
    public void positivePermissionToNegativeRole() {
        final var userId = -1L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(userId, 1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, userId), exception.getMessages().get(0));
    }

    @Test
    @Order(9)
    public void nullPermissionToZeroRole() {
        final var roleId = 0L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(roleId, null));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, roleId), exception.getMessages().get(0));
    }

    @Test
    @Order(10)
    public void negativePermissionToZeroRole() {
        final var roleId = 0L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(roleId, -1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, roleId), exception.getMessages().get(0));
    }

    @Test
    @Order(11)
    public void zeroPermissionToZeroRole() {
        final var roleId = 0L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(roleId, 0L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, roleId), exception.getMessages().get(0));
    }

    @Test
    @Order(12)
    public void positivePermissionToZeroRole() {
        final var roleId = 0L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(roleId, 1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, roleId), exception.getMessages().get(0));
    }


    @Test
    @Order(13)
    public void nullPermissionToPositiveNotExistRole() {
        final var roleId = 1000L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(roleId, null));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, roleId), exception.getMessages().get(0));
    }

    @Test
    @Order(14)
    public void negativePermissionToPositiveNotExistRole() {
        final var roleId = 1000L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(roleId, -1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, roleId), exception.getMessages().get(0));
    }

    @Test
    @Order(15)
    public void zeroPermissionToPositiveNotExistRole() {
        final var roleId = 1000L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(roleId, 0L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, roleId), exception.getMessages().get(0));
    }

    @Test
    @Order(16)
    public void positivePermissionToPositiveNotExistRole() {
        final var roleId = 1000L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(roleId, 1L));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, ROLE, roleId), exception.getMessages().get(0));
    }

    @Test
    @Order(13)
    public void nullPermissionToRole() {
        final var roleId = role.getId();
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(roleId, null));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, PERMISSION, "null"), exception.getMessages().get(0));
    }

    @Test
    @Order(14)
    public void negativePermissionToRole() {
        final var roleId = this.role.getId();
        final var permId = -1L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(roleId, permId));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, PERMISSION, permId), exception.getMessages().get(0));
    }

    @Test
    @Order(15)
    public void zeroPermissionToRole() {
        final var roleId = role.getId();
        final var permId = 0L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(roleId, permId));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, PERMISSION, permId), exception.getMessages().get(0));
    }

    @Test
    @Order(16)
    public void positiveNotExistPermissionToRole() {
        final var roleId = role.getId();
        final var permId = 1000L;
        final var exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> assignService.assignRolePermission(roleId, permId));
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessages());
        Assertions.assertEquals(1, exception.getMessages().size());
        Assertions.assertEquals(format(VALIDATION_NOT_FOUND_MSG, PERMISSION, permId), exception.getMessages().get(0));
    }

    @Test
    @Order(16)
    public void permissionToRole() throws ResourceNotFoundException {
        assignService.assignRolePermission(role.getId(), permission.getId());
        final var resp = roleRepository.findById(role.getId());
        Assertions.assertNotNull(resp);
        Assertions.assertTrue(resp.isPresent());
        final var actual = resp.get();
        Assertions.assertNotNull(actual.getPermissions());
        Assertions.assertEquals(1, actual.getPermissions().size());
        final var actualRole = actual.getPermissions().get(0);
        Assertions.assertEquals(permission, actualRole);
    }

}
