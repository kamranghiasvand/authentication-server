package com.bluebox.planner.auth.persistence.entity;

import com.bluebox.planner.auth.persistence.entity.regular.RegularRoleEntity;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import javax.persistence.*;

import java.util.List;

import static com.bluebox.planner.auth.common.Constants.UNIQUE_PERMISSION_NAME;
import static com.bluebox.planner.auth.common.Constants.UNIQUE_USER_EMAIL;

/**
 * @author by kamran ghiasvand
 */
@Entity
@Setter
@Table(name = "tbl_permission",
        uniqueConstraints = {@UniqueConstraint(name = UNIQUE_PERMISSION_NAME, columnNames = "name")})
public class PermissionEntity extends BaseEntity<Long>{
    private String url;
    private HttpMethod method;
    private String name;
    private List<RegularRoleEntity> roles;

    @Column(name = "url", nullable = false)
    public String getUrl() {
        return url;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="method", nullable = false)
    public HttpMethod getMethod() {
        return method;
    }

    @Column(name = "name",nullable = false)
    public String getName() {
        return name;
    }

    @ManyToMany(mappedBy = "permissions")
    public List<RegularRoleEntity> getRoles() {
        return roles;
    }

}
