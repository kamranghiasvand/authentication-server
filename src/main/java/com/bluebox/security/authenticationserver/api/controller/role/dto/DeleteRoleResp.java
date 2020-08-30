package com.bluebox.security.authenticationserver.api.controller.role.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Kamran Ghiasvand
 */
@Data
public class DeleteRoleResp {
    private Long id;
    private Boolean deleted;
    private String name;
}
