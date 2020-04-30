package com.bluebox.security.authenticationserver.persistence.entity.regular;


import com.bluebox.security.authenticationserver.persistence.entity.UserEntity;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.bluebox.security.authenticationserver.common.Constants.UNIQUE_USER_PHONE;


/**
 * @author by kamran ghiasvand
 */
@Entity
@Setter
@Table(name = "tbl_reg_user",
        uniqueConstraints = @UniqueConstraint(name = UNIQUE_USER_PHONE, columnNames = {"phone", "domain"}))
public class RegularUserEntity extends UserEntity<Long> {

    private List<RoleEntity> roles;

    @Column(name = "phone", nullable = false)
    public String getPhone() {
        return phone;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tbl_reg_user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "pk_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "pk_id"))
    public List<RoleEntity> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "RegularUserEntity{" +
                "roles=" + roles +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isEnabled=" + isEnabled +
                ", registrationDate=" + registrationDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", domain='" + domain + '\'' +
                ", id=" + id +
                '}';
    }
}
