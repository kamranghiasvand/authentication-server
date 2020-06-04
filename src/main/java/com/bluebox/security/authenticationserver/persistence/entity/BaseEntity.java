package com.bluebox.security.authenticationserver.persistence.entity;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author by kamran ghiasvand
 */
@Setter
@ToString
@EqualsAndHashCode
@MappedSuperclass
public class BaseEntity<I extends Serializable> {
    protected I id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    @Column(name = "pk_id")
    public I getId() {
        return id;
    }
}
