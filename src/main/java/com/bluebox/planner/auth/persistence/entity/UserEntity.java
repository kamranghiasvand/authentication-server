package com.bluebox.planner.auth.persistence.entity;

import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author by kamran ghiasvand
 */
@MappedSuperclass
@Setter
public class UserEntity<I extends Serializable> extends BaseEntity<I> {
    private String email;
    private String password;
    private boolean isEnabled=true;

    @Column(name = "email", nullable = false )
    public String getEmail() {
        return email;
    }
    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }
    @Column(name = "enabled")
    public boolean isEnabled() {
        return isEnabled;
    }
}
