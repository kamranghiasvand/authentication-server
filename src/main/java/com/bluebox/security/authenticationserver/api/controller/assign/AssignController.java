package com.bluebox.security.authenticationserver.api.controller.assign;

import com.bluebox.security.authenticationserver.common.PathConstant;
import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.persistence.service.AssignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by kamran ghiasvand
 */
@RestController
@RequestMapping(PathConstant.ASSIGN_BASE)
public class AssignController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssignController.class);
    private final AssignService assignService;

    @Autowired
    public AssignController(AssignService assignService) {
        this.assignService = assignService;
    }

    @RequestMapping(value = PathConstant.ROLE_PERMISSION, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> assignRolePermission(@RequestParam("roleId") Long roleId,
                                                  @RequestParam("permissionId") Long permissionId) throws GlobalException {
        assignService.assignRolePermission(roleId, permissionId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = PathConstant.ROLE_PERMISSION, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> unAssignRolePermission(@RequestParam("roleId") Long roleId,
                                                    @RequestParam("permissionId") Long permissionId) throws GlobalException {
        assignService.unAssignRolePermission(roleId, permissionId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = PathConstant.USER_ROLE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> assignUserRole(@RequestParam("userId") Long userId,
                                            @RequestParam("roleId") Long roleId) throws GlobalException {
        assignService.assignUserRole(userId, roleId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = PathConstant.USER_ROLE, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> unAssignUserRole(@RequestParam("userId") Long userId,
                                              @RequestParam("roleId") Long roleId) throws GlobalException {
        assignService.unAssignUserRole(userId, roleId);
        return ResponseEntity.noContent().build();
    }

}
