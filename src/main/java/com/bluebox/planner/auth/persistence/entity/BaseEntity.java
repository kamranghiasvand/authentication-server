package com.bluebox.planner.auth.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author by kamran ghiasvand
 */
@Setter
@Getter
public class BaseEntity<I extends Serializable> {
    protected I id;
}
