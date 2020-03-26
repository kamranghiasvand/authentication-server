//package com.bluebox.planner.auth.persistence.entity;
//
//import lombok.Setter;
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//
//import static com.bluebox.planner.auth.common.Constants.*;
//
///**
// * @author by kamran ghiasvand
// */
//@Entity
//@Setter
//@Table(name = "tbl_role_permission")
//public class RolePermissionEntity extends BaseEntity{
//    private RoleEntity role;
//    private PermissionEntity permission;
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
//    @GenericGenerator(
//            name = "native",
//            strategy = "native"
//    )
//    @Column(name = "pk_id")
//    public final Long getId() {
//        return id;
//    }
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "fk_role_id", foreignKey = @ForeignKey(name = FK_ROLE_TO_ROLE_PERMISSION))
//    public RoleEntity getRole() {
//        return role;
//    }
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "fk_permission_id", foreignKey = @ForeignKey(name = FK_PERMISSION_TO_ROLE_PERMISSION))
//    public PermissionEntity getPermission() {
//        return permission;
//    }
//
//
//}
