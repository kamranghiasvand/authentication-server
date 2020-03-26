package com.bluebox.planner.auth.persistence.entity;

import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author by kamran ghiasvand
 */
@Entity
@Setter
@Table(name = "tbl_user")
public class UserEntity  extends BaseEntity<Long>{
    private String email;
    private String password;
    private String firstName;
    private String lastName;

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

    @Column(name = "email", nullable = false,unique = true)
    public String getEmail() {
        return email;
    }
    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }
}
