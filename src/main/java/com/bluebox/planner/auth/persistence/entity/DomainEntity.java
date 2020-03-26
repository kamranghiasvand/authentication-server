package com.bluebox.planner.auth.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

import static com.bluebox.planner.auth.common.Constants.UNIQUE_NAME_DOMAIN;

/**
 * @author by kamran ghiasvand
 */
@Entity
@Setter
@Table(name = "tbl_domain", uniqueConstraints = {@UniqueConstraint(name = UNIQUE_NAME_DOMAIN, columnNames = "name")})
public class DomainEntity extends BaseEntity<Long>{
    private String name;
    private List<CategoryEntity> categories;
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

    @Column(name = "name", nullable = false,unique = true)
    public String getName() {
        return name;
    }

    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(value = LazyCollectionOption.EXTRA)
    @BatchSize(size = 50)
    public List<CategoryEntity> getCategories() {
        return categories;
    }
}
