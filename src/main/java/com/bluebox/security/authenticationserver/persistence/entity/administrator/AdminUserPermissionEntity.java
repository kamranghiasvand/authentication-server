package com.bluebox.security.authenticationserver.persistence.entity.administrator;

import com.bluebox.security.authenticationserver.persistence.entity.BaseDomainEntity;
import com.bluebox.security.authenticationserver.persistence.entity.PermissionEntity;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

import static com.bluebox.security.authenticationserver.common.Constants.FK_APE_TO_ADMIN_USER;
import static com.bluebox.security.authenticationserver.common.Constants.FK_APE_TO_PERMISSION;


/**
 * @author by kamran ghiasvand
 */
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "tbl_ad_user_permission")
public class AdminUserPermissionEntity extends BaseDomainEntity<Long> {

    private AdminUserEntity user;
    private PermissionEntity permission;
    private boolean canGrant = false;
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
