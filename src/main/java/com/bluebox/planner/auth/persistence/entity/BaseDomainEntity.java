package com.bluebox.planner.auth.persistence.entity;

import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author by kamran ghiasvand
 */
@Setter
@MappedSuperclass
public class BaseDomainEntity<I extends Serializable> extends BaseEntity<I> {
    private String domain;

    @Column(name = "domain")
    public String getDomain() {
        return domain;
    }
}
