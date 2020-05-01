package com.bluebox.security.authenticationserver.Builder;

import com.bluebox.security.authenticationserver.persistence.entity.PermissionEntity;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RoleEntity;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;

/**
 * @author Kamran Ghiasvand
 */
public class PermissionEntityBuilder {
    private PermissionEntity instance;

    private PermissionEntityBuilder() {
        instance = new PermissionEntity();
    }

    public static PermissionEntityBuilder newBuilder() {
        return new PermissionEntityBuilder();
    }

    public PermissionEntityBuilder method(HttpMethod method) {
        instance.setMethod(method);
        return this;
    }

    public PermissionEntityBuilder url(String url) {
        instance.setUrl(url);
        return this;
    }

    public PermissionEntityBuilder name(String name) {
        instance.setName(name);
        return this;
    }

    public PermissionEntityBuilder domain(String domain) {
        instance.setDomain(domain);
        return this;
    }

    public PermissionEntityBuilder addRoles(RoleEntity... roles) {
        if (roles == null)
            return this;
        if (instance.getRoles() == null)
            instance.setRoles(new ArrayList<>());
        for (RoleEntity role : roles) {
            instance.getRoles().add(role);
        }
        return this;
    }

    public PermissionEntity build() {
        return instance;
    }


}
