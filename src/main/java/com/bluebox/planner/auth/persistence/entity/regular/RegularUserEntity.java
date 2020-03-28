package com.bluebox.planner.auth.persistence.entity.regular;

import com.bluebox.planner.auth.persistence.entity.UserEntity;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.bluebox.planner.auth.common.Constants.UNIQUE_USER_EMAIL;

/**
 * @author by kamran ghiasvand
 */
@Entity
@Setter
@Table(name = "tbl_reg_user",
        uniqueConstraints = {@UniqueConstraint(name = UNIQUE_USER_EMAIL, columnNames = "email")})
public class RegularUserEntity extends UserEntity<Long> {

    private String firstName;
    private String lastName;
    private List<RegularRoleEntity> roles;

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    @ManyToMany
    @JoinTable(name = "tbl_reg_user_role", joinColumns = @JoinColumn(name = "fk_user_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_role_id"))
    public List<RegularRoleEntity> getRoles() {
        return roles;
    }
}
