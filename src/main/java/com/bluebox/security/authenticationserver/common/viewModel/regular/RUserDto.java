package com.bluebox.security.authenticationserver.common.viewModel.regular;

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
public class RUserDto extends BaseDto<Long> {
    @JsonView({ViewRegularUser.PublicRequest.class})
    private String firstName;
    @JsonView({ViewRegularUser.PublicRequest.class})
    private String lastName;
    @JsonView({ViewRegularUser.CreateRequest.class, ViewRegularUser.Response.class, ViewRegularUser.LoginRequest.class})
    private String phone;
    @JsonView({ViewRegularUser.PublicRequest.class, ViewRegularUser.LoginRequest.class})
    private String email;
    @JsonView({ViewRegularUser.CreateRequest.class, ViewRegularUser.UpdateRequest.class, ViewRegularUser.LoginRequest.class})
    private String password;
    @JsonView({ViewRegularUser.CreateRequest.class, ViewRegularUser.UpdateRequest.class})
    private String matchingPassword;
    @JsonView({ViewRegularUser.PublicRequest.class})
    private boolean isEnabled;
    @JsonView({ViewRegularUser.Response.class})
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
