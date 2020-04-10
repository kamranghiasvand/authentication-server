package com.bluebox.planner.auth.persistence.entity;

import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author by kamran ghiasvand
 */
@Entity
@Setter
@Table(name = "tbl_phone_verification")
public class PhoneVerificationEntity extends BaseEntity<Long> {
    private String phoneNumber;
    private Timestamp sentTime;
    private String code;

    @Column(name = "phone_number",nullable = false)
    public String getPhoneNumber() {
        return phoneNumber;
    }
    @Column(name = "sent_time",nullable = false)
    public Timestamp getSentTime() {
        return sentTime;
    }
    @Column(name = "code",nullable = false)
    public String getCode() {
        return code;
    }

}
