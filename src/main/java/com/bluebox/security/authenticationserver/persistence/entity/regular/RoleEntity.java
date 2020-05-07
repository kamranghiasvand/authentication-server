package com.bluebox.security.authenticationserver.persistence.entity.regular;

import com.bluebox.security.authenticationserver.persistence.entity.BaseDomainEntity;
import com.bluebox.security.authenticationserver.persistence.entity.PermissionEntity;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

import static com.bluebox.security.authenticationserver.common.Constants.FK_PERMISSION_TO_ROLE;
import static com.bluebox.security.authenticationserver.common.Constants.FK_ROLE_TO_PERMISSION;


/**
 * @author by kamran ghiasvand
 */
@Setter
@ToString(callSuper = true, exclude = {"users"})
@Entity
@Table(name = "tbl_reg_role")
public class RoleEntity extends BaseDomainEntity<Long> {
    protected List<PermissionEntity> permissions;
    private List<RegularUserEntity> users;
    private String name;

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tbl_reg_role_permission",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "pk_id",
                    foreignKey = @ForeignKey(name = FK_ROLE_TO_PERMISSION)),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "pk_id",
                    foreignKey = @ForeignKey(name = FK_PERMISSION_TO_ROLE)
            ))
    public List<PermissionEntity> getPermissions() {
        return permissions;
    }

    @ManyToMany(mappedBy = "roles")
    public List<RegularUserEntity> getUsers() {
        return users;
    }
}
