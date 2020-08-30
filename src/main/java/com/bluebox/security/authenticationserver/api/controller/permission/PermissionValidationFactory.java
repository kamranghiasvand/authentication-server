package com.bluebox.security.authenticationserver.api.controller.permission;

import com.bluebox.security.authenticationserver.api.controller.permission.dto.CreatePermissionReq;
import com.bluebox.security.authenticationserver.api.controller.permission.dto.DeletePermissionReq;
import com.bluebox.security.authenticationserver.api.controller.permission.dto.UpdatePermissionReq;
import com.bluebox.security.authenticationserver.common.config.InputProperties;
import com.bluebox.security.authenticationserver.validators.ValidationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.bluebox.security.authenticationserver.common.Constants.*;
import static com.bluebox.security.authenticationserver.validators.RuleFactory.*;

/**
 * @author by kamran ghiasvand
 */
@Service
public class PermissionValidationFactory {
    public ValidationContext<CreatePermissionReq> createCtx = new ValidationContext<>();
    public ValidationContext<UpdatePermissionReq> updateCtx = new ValidationContext<>();
    public ValidationContext<DeletePermissionReq> deleteCtx = new ValidationContext<>();
    private final InputProperties inputProps;

    @Autowired
    public PermissionValidationFactory(InputProperties inputProperties) {
        this.inputProps = inputProperties;
        initCreate();
        initUpdate();
        initDelete();
        initSearch();
    }

    private void initCreate() {
        var nullMethod = nullRule(CreatePermissionReq::getMethod, FIELD_METHOD);
        createCtx.addAll(nullMethod);

        var name = nullEmptyLenRule(CreatePermissionReq::getName, FIELD_NAME, inputProps.getTextMaxLength());
        createCtx.addAll(name.getNullRule(), name.getEmptyRule(), name.getLenRule());

        var url = nullEmptyLenRule(CreatePermissionReq::getUrl, FIELD_URL, inputProps.getTextMaxLength());
        createCtx.addAll(url.getNullRule(), url.getEmptyRule(), url.getLenRule());
    }

    private void initUpdate() {
        final var nullId = nullRule(UpdatePermissionReq::getId, FIELD_ID);
        final var negativeId = negativeRule(UpdatePermissionReq::getId, FIELD_ID);
        updateCtx.addAll(nullId, negativeId);

        var name = nullEmptyLenRule(UpdatePermissionReq::getName, FIELD_NAME, inputProps.getTextMaxLength());
        updateCtx.addAll(name.getEmptyRule(), name.getLenRule());

        var url = nullEmptyLenRule(UpdatePermissionReq::getUrl, FIELD_URL, inputProps.getTextMaxLength());
        updateCtx.addAll(url.getEmptyRule(), url.getLenRule());
    }

    private void initDelete() {
        final var nullId = nullRule(DeletePermissionReq::getId, FIELD_ID);
        final var negativeId = negativeRule(DeletePermissionReq::getId, FIELD_ID);
        deleteCtx.addAll(nullId, negativeId);
    }

    private void initSearch() {
    }
}
