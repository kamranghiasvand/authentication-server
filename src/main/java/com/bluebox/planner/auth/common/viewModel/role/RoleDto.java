package com.bluebox.planner.auth.common.viewModel.role;

import com.bluebox.planner.auth.common.viewModel.BaseDto;
import com.bluebox.planner.auth.common.viewModel.permission.PermissionDto;
import com.bluebox.planner.auth.common.viewModel.regular.RUserDto;
import com.bluebox.planner.auth.common.viewModel.views.ViewPermission;
import com.bluebox.planner.auth.common.viewModel.views.ViewRole;
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
    private String name;

    private List<RUserDto> users;
    @JsonView({ViewRole.PublicRequest.class})
    protected List<PermissionDto> permissions;

    @JsonView({ViewPermission.UpdateRequest.class, ViewPermission.Response.class})
    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @JsonView({ViewPermission.UpdateRequest.class, ViewPermission.Response.class})
    @Override
    public Long getId() {
        return super.getId();
    }
}
