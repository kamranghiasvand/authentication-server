package com.bluebox.planner.auth.persistence.entity.administrator;

import com.bluebox.planner.auth.persistence.entity.BaseEntity;
import com.bluebox.planner.auth.persistence.entity.PermissionEntity;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author by kamran ghiasvand
 */
@Entity
@Setter
@Table(name = "tbl_ad_user_permission")
public class AdminUserPermissionEntity extends BaseEntity<Long> {

    private AdminUserEntity user;
    private PermissionEntity permission;
    private boolean canGrant=false;
    private Timestamp assignTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public AdminUserEntity getUser() {
        return user;
    }

    @ManyToOne
    @JoinColumn(name = "permission_id")
    public PermissionEntity getPermission() {
        return permission;
    }
}
