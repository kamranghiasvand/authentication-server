package com.bluebox.security.authenticationserver.api.controller.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author by kamran ghiasvand
 */
@Setter
@Getter
@NoArgsConstructor
public class CreateUserReq {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
}
