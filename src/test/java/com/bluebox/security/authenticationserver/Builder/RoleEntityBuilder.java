package com.bluebox.security.authenticationserver.Builder;

import com.bluebox.security.authenticationserver.persistence.entity.PermissionEntity;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RegularUserEntity;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RoleEntity;

import java.util.ArrayList;

/**
 * @author by kamran ghiasvand
 */
public class RoleEntityBuilder {
    private RoleEntity instance;

    private RoleEntityBuilder() {
        instance = new RoleEntity();
        instance.setPermissions(new ArrayList<>());
        instance.setUsers(new ArrayList<>());
    }

    public static RoleEntityBuilder newBuilder() {
        return new RoleEntityBuilder();
    }

    public RoleEntityBuilder name(String name) {
        instance.setName(name);
        return this;
    }

    public RoleEntityBuilder domain(String domain) {
        instance.setDomain(domain);
        return this;
    }

    public RoleEntityBuilder id(Long id) {
        instance.setId(id);
        return this;
    }

    public RoleEntityBuilder users(RegularUserEntity... users) {
        if (users == null || users.length == 0)
            return this;
        for (RegularUserEntity user : users)
            instance.getUsers().add(user);
        return this;
    }

    public RoleEntityBuilder permission(PermissionEntity... permissions) {
        if (permissions == null || permissions.length == 0)
            return this;
        for (PermissionEntity permission : permissions)
            instance.getPermissions().add(permission);
        return this;
    }

    public RoleEntity build() {
        return this.instance;
    }
}
