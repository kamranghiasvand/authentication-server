package com.bluebox.security.authenticationserver.common.viewModel.permission;


import com.bluebox.security.authenticationserver.common.viewModel.BaseDto;
import com.bluebox.security.authenticationserver.common.viewModel.views.ViewPermission;
import com.bluebox.security.authenticationserver.common.viewModel.views.ViewRole;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpMethod;

/**
 * @author by kamran ghiasvand
 */
@Setter
@Getter
@NoArgsConstructor
public class PermissionDto extends BaseDto<Long> {
    @JsonView({ViewPermission.PublicRequest.class, ViewRole.Response.class})
    private String url;
    @JsonView({ViewPermission.PublicRequest.class,ViewRole.Response.class})
    private HttpMethod method;
    @JsonView({ViewPermission.PublicRequest.class,ViewRole.Response.class})
    private String name;

    @JsonView({ViewPermission.UpdateRequest.class, ViewPermission.Response.class,ViewRole.PublicRequest.class})
    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @JsonView({ViewPermission.UpdateRequest.class, ViewPermission.Response.class, ViewRole.PublicRequest.class})
    @Override
    public Long getId() {
        return super.getId();
    }
}
