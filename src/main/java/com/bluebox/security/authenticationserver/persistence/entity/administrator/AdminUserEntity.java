package com.bluebox.security.authenticationserver.persistence.entity.administrator;


import com.bluebox.security.authenticationserver.persistence.entity.UserEntity;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.bluebox.security.authenticationserver.common.Constants.FK_ADMIN_USER_TO_PARENT;
import static com.bluebox.security.authenticationserver.common.Constants.UNIQUE_ADMIN_EMAIL;


/**
 * @author by kamran ghiasvand
 */
@Entity
@Setter
@Table(name = "tbl_ad_user",
        uniqueConstraints = {@UniqueConstraint(name = UNIQUE_ADMIN_EMAIL, columnNames = "email")})
public class AdminUserEntity extends UserEntity<Long> {
    private AdminUserEntity parent;
    private List<AdminUserEntity> children;
    private List<AdminUserPermissionEntity> permissions = new ArrayList<>();

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name =
            FK_ADMIN_USER_TO_PARENT), referencedColumnName = "pk_id")
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
