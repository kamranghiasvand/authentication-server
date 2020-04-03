package com.bluebox.planner.auth.persistence.entity;

import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author by kamran ghiasvand
 */
@MappedSuperclass
@Setter
public class UserEntity<I extends Serializable> extends BaseEntity<I> {
    private String email;
    private String password;
    private boolean isEnabled = true;
    private Timestamp registrationDate;
    private Timestamp lastUpdateDate;

    @Column(name = "email", nullable = false)
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

    @Column(name = "registration_date", nullable = false)
    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    @Column(name = "last_update", nullable = false)
    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    @PrePersist
    public void persist() {
        lastUpdateDate = registrationDate = new Timestamp(System.currentTimeMillis());
    }
    @PreUpdate
    public void update(){
        lastUpdateDate=new Timestamp(System.currentTimeMillis());
    }
}
