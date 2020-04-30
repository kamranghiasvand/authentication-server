package com.bluebox.security.authenticationserver.common.viewModel.role;

import com.bluebox.security.authenticationserver.common.viewModel.BaseDto;
import com.bluebox.security.authenticationserver.common.viewModel.permission.PermissionDto;
import com.bluebox.security.authenticationserver.common.viewModel.regular.RUserDto;
import com.bluebox.security.authenticationserver.common.viewModel.views.ViewPermission;
import com.bluebox.security.authenticationserver.common.viewModel.views.ViewRole;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author by kamran ghiasvand
 */
@Setter
@Getter
@NoArgsConstructor
public class RoleDto extends BaseDto<Long> {
    @JsonView({ViewRole.PublicRequest.class})
    protected List<PermissionDto> permissions;
    @JsonView({ViewRole.PublicRequest.class})
    private String name;
    private List<RUserDto> users;

    @JsonView({ViewPermission.UpdateRequest.class, ViewPermission.Response.class})
    @Override
    public Long getId() {
        return super.getId();
    }

    @JsonView({ViewPermission.UpdateRequest.class, ViewPermission.Response.class})
    @Override
    public void setId(Long id) {
        super.setId(id);
    }
}
