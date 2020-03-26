package com.bluebox.planner.auth.persistence.entity;

import lombok.Setter;

import java.io.Serializable;

/**
 * @author by kamran ghiasvand
 */
@Setter
public class BaseEntity<I extends Serializable> {
    protected I id;
}
