package com.bluebox.security.authenticationserver.common.viewModel;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author by kamran ghiasvand
 */
@Getter
@Setter
public class BaseDto<I extends Serializable> {
    private I id;
}
