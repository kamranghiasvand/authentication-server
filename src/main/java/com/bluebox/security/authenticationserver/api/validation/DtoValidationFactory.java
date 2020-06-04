package com.bluebox.security.authenticationserver.api.validation;


import com.bluebox.security.authenticationserver.common.Constants;
import com.bluebox.security.authenticationserver.common.config.InputProperties;
import com.bluebox.security.authenticationserver.common.viewModel.BaseDto;
import com.bluebox.security.authenticationserver.validators.RuleFactory;
import com.bluebox.security.authenticationserver.validators.ValidationContext;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author Yaser(amin) Sadeghi
 */
public abstract class DtoValidationFactory<D extends BaseDto<I>, I extends Serializable> {
    @Getter
    protected ValidationContext<D> createCtx = new ValidationContext<>();
    @Getter
    protected ValidationContext<D> updateCtx = new ValidationContext<>();
    protected InputProperties inputProps;

    protected DtoValidationFactory(InputProperties inputProps) {
        this.inputProps = inputProps;
        updateCtx.addAll(
                RuleFactory.nullRule(BaseDto::getId, Constants.FIELD_ID));
        init();
    }

    protected abstract void init();
}
