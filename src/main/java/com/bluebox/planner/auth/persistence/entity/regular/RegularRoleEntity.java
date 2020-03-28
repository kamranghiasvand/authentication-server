package com.bluebox.planner.auth.persistence.entity.regular;

import com.bluebox.planner.auth.persistence.entity.BaseEntity;
import com.bluebox.planner.auth.persistence.entity.PermissionEntity;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author by kamran ghiasvand
 */
@Entity
@Setter
@Table(name = "tbl_reg_role")
public class RegularRoleEntity extends BaseEntity<Long> {
    private List<RegularUserEntity> users;
    private String name;
    protected List<PermissionEntity> permissions;

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @ManyToMany
    @JoinTable(name = "tbl_reg_role_permission", joinColumns = @JoinColumn(name = "fk_role_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_permission_id"))
    public List<PermissionEntity> getPermissions() {
        return permissions;
    }

    @ManyToMany(mappedBy = "roles")
    public List<RegularUserEntity> getUsers() {
        return users;
    }
}
