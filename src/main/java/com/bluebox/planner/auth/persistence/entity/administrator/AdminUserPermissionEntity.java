package com.bluebox.planner.auth.persistence.entity.administrator;

import com.bluebox.planner.auth.persistence.entity.BaseEntity;
import com.bluebox.planner.auth.persistence.entity.PermissionEntity;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

import static com.bluebox.planner.auth.common.Constants.FK_APE_TO_ADMIN_USER;
import static com.bluebox.planner.auth.common.Constants.FK_APE_TO_PERMISSION;

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

    @Column(name = "can_grant")
    public boolean isCanGrant() {
        return canGrant;
    }

    @Column(name = "assign_time")
    public Timestamp getAssignTime() {
        return assignTime;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name =
            FK_APE_TO_ADMIN_USER))
    public AdminUserEntity getUser() {
        return user;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_id", foreignKey = @ForeignKey(name =
            FK_APE_TO_PERMISSION))
    public PermissionEntity getPermission() {
        return permission;
    }
}
