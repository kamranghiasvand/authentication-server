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
public class BaseEntity<I extends Serializable> {
    protected I id;
    private String domain;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    @Column(name = "pk_id")
    public final I getId() {
        return id;
    }

    @Column(name = "domain")
    public String getDomain() {
        return domain;
    }
}