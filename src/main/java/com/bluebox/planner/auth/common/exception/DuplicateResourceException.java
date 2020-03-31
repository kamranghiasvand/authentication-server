package com.bluebox.planner.auth.common.exception;

import com.bluebox.planner.auth.common.Constants;
import org.springframework.http.HttpStatus;

/**
 * @author Yaser(amin) Sadeghi
 */
public class DuplicateResourceException extends GlobalException {

    public DuplicateResourceException(String message) {
        super(Constants.ERROR_DUPLICATE, HttpStatus.CONFLICT);
        this.getMessages().add(message);
    }
}
