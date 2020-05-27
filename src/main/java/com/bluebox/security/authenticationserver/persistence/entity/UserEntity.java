package com.bluebox.security.authenticationserver.persistence.entity;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author by kamran ghiasvand
 */
@Setter
@ToString(callSuper = true, exclude = {"password"})
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class UserEntity<I extends Serializable> extends BaseDomainEntity<I> {

    protected String firstName;
    protected String lastName;
    protected String phone;
    protected String email;
    protected String password;
    protected boolean isEnabled = true;
    protected Timestamp registrationDate;
    protected Timestamp lastUpdateDate;

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
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
    public void update() {
        lastUpdateDate = new Timestamp(System.currentTimeMillis());
    }

}
