package com.bluebox.security.authenticationserver.api.controller.user;

import com.bluebox.security.authenticationserver.api.controller.permission.dto.CreatePermissionReq;
import com.bluebox.security.authenticationserver.api.controller.permission.dto.DeletePermissionReq;
import com.bluebox.security.authenticationserver.api.controller.permission.dto.UpdatePermissionReq;
import com.bluebox.security.authenticationserver.common.config.InputProperties;
import com.bluebox.security.authenticationserver.common.viewModel.regular.RUserDto;
import com.bluebox.security.authenticationserver.validators.Rule;
import com.bluebox.security.authenticationserver.validators.ValidationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bluebox.security.authenticationserver.common.Constants.*;
import static com.bluebox.security.authenticationserver.validators.RuleFactory.*;
import static java.text.MessageFormat.format;

/**
 * @author by kamran ghiasvand
 */
@Service
public class UserValidationFactory {

    public ValidationContext<CreatePermissionReq> createCtx = new ValidationContext<>();
    public ValidationContext<UpdatePermissionReq> updateCtx = new ValidationContext<>();
    public ValidationContext<DeletePermissionReq> deleteCtx = new ValidationContext<>();
    private final InputProperties inputProps;

    @Autowired
    public UserValidationFactory(InputProperties inputProperties) {
        this.inputProps = inputProperties;
        initCreate();
        initUpdate();
        initDelete();
        initSearch();
    }

    private void initCreate() {
        var nullPhone = nullRule(Create::getPhone, FIELD_USER_PHONE);
        var validPhone = validPhone(RUserDto::getPhone, FIELD_USER_PHONE);
        var firstName = nullEmptyLenRule(RUserDto::getFirstName,
                FIELD_REGULAR_USER_FIRST_NAME, inputProps.getTextMaxLength());
        var lastName = nullEmptyLenRule(RUserDto::getLastName,
                FIELD_REGULAR_USER_LAST_NAME, inputProps.getTextMaxLength());
        var password = nullEmptyLenRule(RUserDto::getPassword, FIELD_REGULAR_USER_PASSWORD, inputProps.getTextMaxLength());
        var matchingPassword = nullEmptyLenRule(RUserDto::getPassword, FIELD_REGULAR_USER_PASSWORD, inputProps.getTextMaxLength());
        Rule<RUserDto> samePassword = dto -> {
            if (dto.getPassword() == null)
                return null;
            if (!dto.getPassword().equals(dto.getMatchingPassword()))
                return single(format(FIRST_IS_NOT_EQUAL_TO_SECOND_MSG, FIELD_REGULAR_USER_PASSWORD, FIELD_REGULAR_USER_MATCHING_PASSWORD));
            return null;
        };

        createCtx.addAll(nullPhone, validPhone,
                firstName.getNullRule(), firstName.getEmptyRule(), firstName.getLenRule(),
                lastName.getNullRule(), lastName.getEmptyRule(), lastName.getLenRule(),
                password.getNullRule(), password.getEmptyRule(), password.getLenRule(),
                matchingPassword.getNullRule(), matchingPassword.getEmptyRule(), matchingPassword.getLenRule(),
                samePassword);
    }

    private void initUpdate() {
    }

    private void initDelete() {
    }

    private void initSearch() {
    }

    @Override
    protected void init() {
        var nullPhone = nullRule(RUserDto::getPhone, FIELD_USER_PHONE);
        var validPhone = validPhone(RUserDto::getPhone, FIELD_USER_PHONE);
        var firstName = nullEmptyLenRule(RUserDto::getFirstName,
                FIELD_REGULAR_USER_FIRST_NAME, inputProps.getTextMaxLength());
        var lastName = nullEmptyLenRule(RUserDto::getLastName,
                FIELD_REGULAR_USER_LAST_NAME, inputProps.getTextMaxLength());
        var password = nullEmptyLenRule(RUserDto::getPassword, FIELD_REGULAR_USER_PASSWORD, inputProps.getTextMaxLength());
        var matchingPassword = nullEmptyLenRule(RUserDto::getPassword, FIELD_REGULAR_USER_PASSWORD, inputProps.getTextMaxLength());
        Rule<RUserDto> samePassword = dto -> {
            if (dto.getPassword() == null)
                return null;
            if (!dto.getPassword().equals(dto.getMatchingPassword()))
                return single(format(FIRST_IS_NOT_EQUAL_TO_SECOND_MSG, FIELD_REGULAR_USER_PASSWORD, FIELD_REGULAR_USER_MATCHING_PASSWORD));
            return null;
        };

        createCtx.addAll(nullPhone, validPhone,
                firstName.getNullRule(), firstName.getEmptyRule(), firstName.getLenRule(),
                lastName.getNullRule(), lastName.getEmptyRule(), lastName.getLenRule(),
                password.getNullRule(), password.getEmptyRule(), password.getLenRule(),
                matchingPassword.getNullRule(), matchingPassword.getEmptyRule(), matchingPassword.getLenRule(),
                samePassword);
        updateCtx.addAll(
                firstName.getEmptyRule(), firstName.getLenRule(),
                lastName.getEmptyRule(), lastName.getLenRule(),
                password.getEmptyRule(), password.getLenRule(),
                samePassword);
    }

    private List<String> single(String mess) {
        return java.util.Collections.singletonList(mess);
    }
}
