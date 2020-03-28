package com.bluebox.planner.auth.persistence.entity.administrator;

import com.bluebox.planner.auth.persistence.entity.UserEntity;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.bluebox.planner.auth.common.Constants.UNIQUE_USER_EMAIL;

/**
 * @author by kamran ghiasvand
 */
@Entity
@Setter
@Table(name = "tbl_ad_user",
        uniqueConstraints = {@UniqueConstraint(name = UNIQUE_USER_EMAIL, columnNames = "email")})
public class AdminUserEntity extends UserEntity<Long> {
    private Long parentId;
    private List<AdminUserEntity> children;
    private List<AdminUserPermissionEntity> permissions=new ArrayList<>();

    @Column(name = "parent_id")
    public Long getParentId() {
        return parentId;
    }

    @OneToMany(mappedBy = "parentId",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<AdminUserEntity> getChildren() {
        return children;
    }

    @OneToMany(mappedBy = "user")
    public List<AdminUserPermissionEntity> getPermissions() {
        return permissions;
    }
}
