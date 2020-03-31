package com.bluebox.planner.auth.common.viewModel.regular;

import com.bluebox.planner.auth.common.viewModel.BaseDto;
import com.bluebox.planner.auth.common.viewModel.views.ViewRegularUser;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author by kamran ghiasvand
 */
@Setter
@Getter
@NoArgsConstructor
public class RegularUserDto extends BaseDto<Long> {
    @JsonView({ViewRegularUser.PublicRequest.class})
    private String firstName;
    @JsonView({ViewRegularUser.PublicRequest.class})
    private String lastName;
    @JsonView({ViewRegularUser.PublicRequest.class})
    private String email;
    @JsonView({ViewRegularUser.CreateRequest.class, ViewRegularUser.UpdateRequest.class})
    private String password;
    @JsonView({ViewRegularUser.CreateRequest.class, ViewRegularUser.UpdateRequest.class})
    private String matchingPassword;
    @JsonView({ViewRegularUser.PublicRequest.class})
    private boolean isEnabled;

    @JsonView({ViewRegularUser.UpdateRequest.class, ViewRegularUser.Response.class})
    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @JsonView({ViewRegularUser.UpdateRequest.class, ViewRegularUser.Response.class})
    @Override
    public Long getId() {
        return super.getId();
    }
}
