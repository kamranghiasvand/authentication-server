package com.bluebox.security.authenticationserver.persistence.service;

import com.bluebox.security.authenticationserver.common.exception.ResourceNotFoundException;

/**
 * @author Kamran Ghiasvand
 */
public interface AssignService {
    void assignUserRole(Long userId, Long roleId) throws ResourceNotFoundException;

    void unAssignUserRole(Long userId, Long roleId) throws ResourceNotFoundException;

    void assignRolePermission(Long roleId, Long permissionId) throws ResourceNotFoundException;

    void unAssignRolePermission(Long roleId, Long permissionId) throws ResourceNotFoundException;
}
