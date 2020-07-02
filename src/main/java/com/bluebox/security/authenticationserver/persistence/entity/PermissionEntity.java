package com.bluebox.security.authenticationserver.persistence.entity;


import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpMethod;

import javax.persistence.*;

import static com.bluebox.security.authenticationserver.common.Constants.UNIQUE_PERMISSION_NAME;


/**
 * @author by kamran ghiasvand
 */
@Entity
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "tbl_permission",
        uniqueConstraints = {@UniqueConstraint(name = UNIQUE_PERMISSION_NAME, columnNames = {"name", "domain"})})
public class PermissionEntity extends BaseDomainEntity<Long> {
    private String url;
    private HttpMethod method;
    private String name;

    @Column(name = "url", nullable = false)
    public String getUrl() {
        return url;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false)
    public HttpMethod getMethod() {
        return method;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }


}
