package com.bluebox.planner.auth.integration.validation;


import com.bluebox.planner.auth.common.Constants;
import com.bluebox.planner.auth.common.config.InputProperties;
import com.bluebox.planner.auth.common.viewModel.BaseDto;
import com.bluebox.planner.auth.validators.RuleFactory;
import com.bluebox.planner.auth.validators.ValidationContext;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author Yaser(amin) Sadeghi
 */
public abstract class ValidationFactory<D extends BaseDto<I>,I extends Serializable> {
    @Getter
    protected ValidationContext<D> createCtx = new ValidationContext<>();
    @Getter
    protected ValidationContext<D> updateCtx = new ValidationContext<>();
    protected InputProperties inputProps;

    protected ValidationFactory(InputProperties inputProps) {
        this.inputProps = inputProps;
        updateCtx.addAll(
                RuleFactory.nullRule(BaseDto::getId, Constants.FIELD_ID));
        init();
    }

    protected abstract void init();
}
