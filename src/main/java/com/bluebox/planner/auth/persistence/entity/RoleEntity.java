package com.bluebox.planner.auth.persistence.entity;

import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

import java.util.List;

import static com.bluebox.planner.auth.common.Constants.FK_CATEGORY_TO_DOMAIN;
import static com.bluebox.planner.auth.common.Constants.FK_ROLE_TO_CATEGORY;

/**
 * @author by kamran ghiasvand
 */
@Entity
@Setter
@Table(name = "tbl_role")
public class RoleEntity extends BaseEntity<Long> {
    private String name;
    private CategoryEntity category;
    private List<PermissionEntity> permissions;

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

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_category_id", foreignKey = @ForeignKey(name = FK_ROLE_TO_CATEGORY))
    public CategoryEntity getCategory() {
        return category;
    }

   @ManyToMany
   @JoinTable(name = "tbl_role_permission",joinColumns = @JoinColumn(name = "fk_role_id"),
   inverseJoinColumns = @JoinColumn(name = "fk_permission_id"))
    public List<PermissionEntity> getPermissions() {
        return permissions;
    }
}
