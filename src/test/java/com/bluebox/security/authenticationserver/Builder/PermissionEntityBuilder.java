package com.bluebox.security.authenticationserver.Builder;

import com.bluebox.security.authenticationserver.persistence.entity.PermissionEntity;
import org.springframework.http.HttpMethod;

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

    public PermissionEntityBuilder id(Long id) {
        instance.setId(id);
        return this;
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

    public PermissionEntity build() {
        return instance;
    }


}
