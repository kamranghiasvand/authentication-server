package com.bluebox.security.authenticationserver.api.controller.user.dto;

import com.bluebox.security.authenticationserver.common.viewModel.BaseDto;
import com.bluebox.security.authenticationserver.common.viewModel.views.ViewRegularUser;
import com.bluebox.security.authenticationserver.common.viewModel.views.ViewRole;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author by kamran ghiasvand
 */
@Setter
@Getter
@NoArgsConstructor
public class CreateUserReq {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private String matchingPassword;
    private Timestamp registrationDate;
    @JsonView({ViewRegularUser.Response.class})
    private Timestamp lastUpdateDate;

    @JsonView({ViewRegularUser.UpdateRequest.class, ViewRegularUser.Response.class})
    @Override
    public Long getId() {
        return super.getId();
    }

    @JsonView({ViewRegularUser.UpdateRequest.class, ViewRegularUser.Response.class,
            ViewRole.CreateRequest.class, ViewRole.UpdateRequest.class})
    @Override
    public void setId(Long id) {
        super.setId(id);
    }
}
