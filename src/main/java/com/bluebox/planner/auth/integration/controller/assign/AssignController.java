package com.bluebox.planner.auth.integration.controller.assign;

import com.bluebox.planner.auth.common.PathConstant;
import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.persistence.service.RolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author by kamran ghiasvand
 */
@RestController
@RequestMapping(PathConstant.ASSIGN_BASE)
public class AssignController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssignController.class);
    private final RolePermissionService rolePermissionService;

    @Autowired
    public AssignController(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @RequestMapping(value = PathConstant.ROLE_PERMISSION, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> assignRolePermission(@RequestParam("roleId") Long roleId,
                                                  @RequestParam("permissionId") Long permissionId) throws GlobalException {
        rolePermissionService.assignRolePermission(roleId, permissionId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = PathConstant.ROLE_PERMISSION, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> unAssignRolePermission(@RequestParam("roleId") Long roleId,
                                                    @RequestParam("permissionId") Long permissionId) throws GlobalException {
        rolePermissionService.unAssignRolePermission(roleId, permissionId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = PathConstant.USER_ROLE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> assignUserRole(@RequestParam("userId") Long userId,
                                            @RequestParam("roleId") Long roleId) throws GlobalException {
        rolePermissionService.assignUserRole(userId, roleId);
        return ResponseEntity.noContent().build();
    }
    @RequestMapping(value = PathConstant.USER_ROLE, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> unAssignUserRole(@RequestParam("userId") Long userId,
                                            @RequestParam("roleId") Long roleId) throws GlobalException {
        rolePermissionService.unAssignUserRole(userId, roleId);
        return ResponseEntity.noContent().build();
    }

}
