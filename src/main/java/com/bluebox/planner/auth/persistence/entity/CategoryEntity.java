package com.bluebox.planner.auth.persistence.entity;

import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

import java.util.List;

import static com.bluebox.planner.auth.common.Constants.FK_CATEGORY_TO_DOMAIN;

/**
 * @author by kamran ghiasvand
 */
@Entity
@Setter
@Table(name = "tbl_category")
public class CategoryEntity extends BaseEntity<Long> {
    private String name;
    private DomainEntity domain;
    private List<RoleEntity> roles;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    @Override
    @Column(name = "pk_id")
    public final Long getId() {
        return id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_domain_id", foreignKey = @ForeignKey(name =
            FK_CATEGORY_TO_DOMAIN))
    public DomainEntity getDomain() {
        return domain;
    }

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(value = LazyCollectionOption.EXTRA)
    @BatchSize(size = 50)
    public List<RoleEntity> getRoles() {
        return roles;
    }
}
