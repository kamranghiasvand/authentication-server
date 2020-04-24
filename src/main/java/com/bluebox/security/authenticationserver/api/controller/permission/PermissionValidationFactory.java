package com.bluebox.security.authenticationserver.api.controller.permission;

import com.bluebox.security.authenticationserver.api.validation.ValidationFactory;
import com.bluebox.security.authenticationserver.common.config.InputProperties;
import com.bluebox.security.authenticationserver.common.viewModel.permission.PermissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.bluebox.security.authenticationserver.common.Constants.*;
import static com.bluebox.security.authenticationserver.validators.RuleFactory.*;

/**
 * @author by kamran ghiasvand
 */
@Service
public class PermissionValidationFactory extends ValidationFactory<PermissionDto, Long> {

    @Autowired
    public PermissionValidationFactory(InputProperties inputProperties) {
        super(inputProperties);

    }

    @Override
    protected void init() {
        var nullMethod = nullRule(PermissionDto::getMethod, FIELD_PERMISSION_METHOD);
        var name = nullEmptyLenRule(PermissionDto::getName, FIELD_PERMISSION_NAME, inputProps.getTextMaxLength());
        var url = nullEmptyLenRule(PermissionDto::getUrl, FIELD_PERMISSION_URL, inputProps.getTextMaxLength());

        createCtx.addAll(nullMethod,
                name.getNullRule(),name.getEmptyRule(),name.getLenRule(),
                url.getNullRule(),url.getEmptyRule(),url.getLenRule());
        updateCtx.addAll(name.getEmptyRule(),name.getLenRule(),
                url.getEmptyRule(),url.getLenRule());
    }
}
