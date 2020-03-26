package com.bluebox.planner.auth.common.exception;

import com.bluebox.planner.auth.common.Constants;
import org.springframework.http.HttpStatus;

/**
 * @author Yaser(amin) Sadeghi
 */
public class ResourceNotFoundException extends GlobalException {

    public ResourceNotFoundException(String message) {
        super(Constants.ERROR_NOT_FOUND, HttpStatus.NOT_FOUND);
        this.getMessages().add(message);
    }

}
