package com.bluebox.security.authenticationserver.api.controller.permission.dto;

import lombok.Data;
import org.springframework.http.HttpMethod;

/**
 * @author Kamran Ghiasvand
 */
@Data
public class DeletePermissionResp {
    private Long id;
    private String url;
    private HttpMethod method;
    private String name;
}
