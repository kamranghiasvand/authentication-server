package com.bluebox.security.authenticationserver.persistence.entity;

import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

import static com.bluebox.security.authenticationserver.common.Constants.UNIQUE_CLIENT_ID;

/**
 * @author Kamran Ghiasvand
 */
@Setter
@Entity
@Table(name = "tbl_client", uniqueConstraints = @UniqueConstraint(name = UNIQUE_CLIENT_ID, columnNames = {"client_id", "domain"}))
public class ClientEntity extends BaseDomainEntity<Long> {
    private String clientId;
    private String password;
    private String description;
    private boolean isEnabled = true;
    private Timestamp registrationDate;
    private Timestamp lastUpdateDate;
    private byte[] logo;

    @Column(name = "client_id", nullable = false)
    public String getClientId() {
        return clientId;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    @Column(name = "enabled")
    public boolean isEnabled() {
        return isEnabled;
    }

    @Column(name = "registration_date", nullable = false)
    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    @Column(name = "last_update", nullable = false)
    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Lob
    @Column(name = "logo")
    public byte[] getLogo() {
        return logo;
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "clientId='" + clientId + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", isEnabled=" + isEnabled +
                ", registrationDate=" + registrationDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", domain='" + domain + '\'' +
                ", id=" + id +
                '}';
    }
}
