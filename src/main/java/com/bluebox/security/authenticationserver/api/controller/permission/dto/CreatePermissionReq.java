package com.bluebox.security.authenticationserver.api.controller.permission.dto;

import lombok.Data;
import org.springframework.http.HttpMethod;

/**
 * @author Kamran Ghiasvand
 */
@Data
public class CreatePermissionReq {
    private String url;
    private HttpMethod method;
    private String name;
}
