package com.bluebox.planner.auth.persistence.entity.regular;

import com.bluebox.planner.auth.persistence.entity.UserEntity;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.bluebox.planner.auth.common.Constants.UNIQUE_USER_PHONE;

/**
 * @author by kamran ghiasvand
 */
@Entity
@Setter
@Table(name = "tbl_reg_user",
        uniqueConstraints = {@UniqueConstraint(name = UNIQUE_USER_PHONE, columnNames = "phone")})
public class RegularUserEntity extends UserEntity<Long> {

    private String firstName;
    private String lastName;
    private List<RoleEntity> roles;

    @Column(name = "phone", nullable = false)
    public String getPhone() {
        return phone;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    @ManyToMany
    @JoinTable(name = "tbl_reg_user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "pk_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "pk_id"))
    public List<RoleEntity> getRoles() {
        return roles;
    }
}
