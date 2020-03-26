package com.bluebox.planner.auth.persistence.entity;

import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.http.HttpMethod;

import javax.persistence.*;

import java.util.List;

import static com.bluebox.planner.auth.common.Constants.FK_CATEGORY_TO_DOMAIN;
import static com.bluebox.planner.auth.common.Constants.FK_PERMISSION_TO_DOMAIN;

/**
 * @author by kamran ghiasvand
 */
@Entity
@Setter
@Table(name = "tbl_permission")
public class PermissionEntity extends BaseEntity<Long>{
    private String url;
    private HttpMethod method;
    private List<RoleEntity> roles;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    @Column(name = "pk_id")
    public final Long getId() {
        return id;
    }

    @ManyToMany(mappedBy = "permissions")
    public List<RoleEntity> getRoles() {
        return roles;
    }

}
