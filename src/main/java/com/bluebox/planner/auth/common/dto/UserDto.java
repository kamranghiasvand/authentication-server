package com.bluebox.planner.auth.common.dto;

import com.bluebox.planner.auth.common.dto.views.ViewUser;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

/**
 * @author by kamran ghiasvand
 */
@Setter
@Getter
public class UserDto extends BaseDto<Long> {
    @JsonView({ViewUser.PublicRequest.class})
    private String email;
    @JsonView({ViewUser.CreateRequest.class, ViewUser.UpdateRequest.class})
    private String password;
    @JsonView({ViewUser.PublicRequest.class})
    private String firstName;
    @JsonView({ViewUser.PublicRequest.class})
    private String lastName;

    @JsonView({ViewUser.Response.class,ViewUser.UpdateRequest.class})
    @Override
    public Long getId() {
        return super.getId();
    }
}
