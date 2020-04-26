package com.bluebox.security.authenticationserver.persistence.entity;

import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author by kamran ghiasvand
 */
@Setter
@MappedSuperclass
public class BaseDomainEntity<I extends Serializable> extends BaseEntity<I> {
    private String domain;

    @Column(name = "domain", nullable = false)
    public String getDomain() {
        return domain;
    }
}
