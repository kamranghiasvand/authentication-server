package com.bluebox.planner.auth.persistence.entity.administrator;

import com.bluebox.planner.auth.persistence.entity.UserEntity;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.bluebox.planner.auth.common.Constants.FK_ADMIN_USER_TO_PARENT;
import static com.bluebox.planner.auth.common.Constants.UNIQUE_USER_EMAIL;

/**
 * @author by kamran ghiasvand
 */
@Entity
@Setter
@Table(name = "tbl_ad_user",
        uniqueConstraints = {@UniqueConstraint(name = UNIQUE_USER_EMAIL, columnNames = "email")})
public class AdminUserEntity extends UserEntity<Long> {
    private AdminUserEntity parent;
    private List<AdminUserEntity> children;
    private List<AdminUserPermissionEntity> permissions = new ArrayList<>();



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name =
            FK_ADMIN_USER_TO_PARENT),referencedColumnName = "pk_id")
    public AdminUserEntity getParent() {
        return parent;
    }

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<AdminUserEntity> getChildren() {
        return children;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    public List<AdminUserPermissionEntity> getPermissions() {
        return permissions;
    }
}
