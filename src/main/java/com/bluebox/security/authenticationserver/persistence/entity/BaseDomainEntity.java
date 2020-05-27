package com.bluebox.security.authenticationserver.persistence.entity;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author by kamran ghiasvand
 */
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@FilterDef(name = "domainFilter", parameters = {@ParamDef(name = "domainParam", type = "string")})
@Filter(name = "domainFilter", condition = "domain= :domainParam")
public class BaseDomainEntity<I extends Serializable> extends BaseEntity<I> {
    protected String domain;

    @Column(name = "domain", nullable = false)
    public String getDomain() {
        return domain;
    }
}
