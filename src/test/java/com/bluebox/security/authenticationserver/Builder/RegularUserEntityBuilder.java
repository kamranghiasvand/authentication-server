package com.bluebox.security.authenticationserver.Builder;

import com.bluebox.security.authenticationserver.persistence.entity.regular.RegularUserEntity;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RoleEntity;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @author Kamran Ghiasvand
 */
public class RegularUserEntityBuilder {
    private RegularUserEntity instance;

    private RegularUserEntityBuilder() {
        instance = new RegularUserEntity();
        instance.setRoles(new ArrayList<>());
    }

    public static RegularUserEntityBuilder newBuilder() {
        return new RegularUserEntityBuilder();
    }

    public RegularUserEntityBuilder id(Long id) {
        instance.setId(id);
        return this;
    }

    public RegularUserEntityBuilder domain(String domain) {
        instance.setDomain(domain);
        return this;
    }

    public RegularUserEntityBuilder email(String email) {
        instance.setEmail(email);
        return this;
    }

    public RegularUserEntityBuilder password(String password) {
        instance.setPassword(password);
        return this;
    }

    public RegularUserEntityBuilder roles(RoleEntity... roles) {
        if (roles == null)
            return this;
        for (RoleEntity role : roles)
            instance.getRoles().add(role);
        return this;
    }

    public RegularUserEntityBuilder firstName(String firstName) {
        instance.setFirstName(firstName);
        return this;
    }

    public RegularUserEntityBuilder enabled(Boolean enabled) {
        instance.setEnabled(enabled);
        return this;
    }

    public RegularUserEntityBuilder lastName(String lastName) {
        instance.setLastName(lastName);
        return this;
    }

    public RegularUserEntityBuilder updateTime(Timestamp updateTime) {
        instance.setLastUpdateDate(updateTime);
        return this;
    }

    public RegularUserEntityBuilder phone(String phone) {
        instance.setPhone(phone);
        return this;
    }

    public RegularUserEntityBuilder registrationDate(Timestamp date) {
        instance.setRegistrationDate(date);
        return this;
    }

    public RegularUserEntity build() {
        return instance;
    }
}
