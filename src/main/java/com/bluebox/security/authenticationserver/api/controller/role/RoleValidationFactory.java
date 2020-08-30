package com.bluebox.security.authenticationserver.api.controller.role;

import com.bluebox.security.authenticationserver.api.controller.role.dto.CreateRoleReq;
import com.bluebox.security.authenticationserver.api.controller.role.dto.DeleteRoleReq;
import com.bluebox.security.authenticationserver.api.controller.role.dto.UpdateRoleReq;
import com.bluebox.security.authenticationserver.common.config.InputProperties;
import com.bluebox.security.authenticationserver.validators.ValidationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.bluebox.security.authenticationserver.common.Constants.FIELD_ID;
import static com.bluebox.security.authenticationserver.common.Constants.FIELD_NAME;
import static com.bluebox.security.authenticationserver.validators.RuleFactory.*;


/**
 * @author by kamran ghiasvand
 */
@Service
public class RoleValidationFactory {

    public ValidationContext<CreateRoleReq> createCtx = new ValidationContext<>();
    public ValidationContext<UpdateRoleReq> updateCtx = new ValidationContext<>();
    public ValidationContext<DeleteRoleReq> deleteCtx = new ValidationContext<>();
    private final InputProperties inputProps;

    @Autowired
    public RoleValidationFactory(InputProperties inputProperties) {
        this.inputProps = inputProperties;
        initCreate();
        initUpdate();
        initDelete();
        initSearch();
    }

    private void initCreate() {
        var name = nullEmptyLenRule(CreateRoleReq::getName, FIELD_NAME, inputProps.getTextMaxLength());
        createCtx.addAll(name.getNullRule(), name.getEmptyRule(), name.getLenRule());
    }

    private void initUpdate() {
        var nullId = nullRule(UpdateRoleReq::getId, FIELD_ID);
        updateCtx.addAll(nullId);

        var negativeId = negativeRule(UpdateRoleReq::getId, FIELD_ID);
        updateCtx.addAll(negativeId);

        var name = nullEmptyLenRule(UpdateRoleReq::getName, FIELD_NAME, inputProps.getTextMaxLength());
        updateCtx.addAll(name.getEmptyRule(), name.getLenRule());
    }

    private void initDelete() {
        var nullId = nullRule(DeleteRoleReq::getId, FIELD_ID);
        deleteCtx.addAll(nullId);

        var negativeId = negativeRule(DeleteRoleReq::getId, FIELD_ID);
        deleteCtx.addAll(negativeId);
    }

    private void initSearch() {
    }
}
