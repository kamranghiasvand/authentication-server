package com.bluebox.security.authenticationserver.api.controller.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kamran Ghiasvand
 */
@Data
@NoArgsConstructor
public class CreateUserResp {
    private Long id;
    private Boolean deleted;
    private String domain;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private boolean isEnabled = true;
    private Long registrationDate;
    private Long lastUpdateDate;
}
