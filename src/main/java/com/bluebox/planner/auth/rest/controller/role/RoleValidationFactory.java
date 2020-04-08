package com.bluebox.planner.auth.rest.controller.role;

import com.bluebox.planner.auth.common.config.InputProperties;
import com.bluebox.planner.auth.common.viewModel.permission.PermissionDto;
import com.bluebox.planner.auth.common.viewModel.role.RoleDto;
import com.bluebox.planner.auth.rest.validation.ValidationFactory;
import com.bluebox.planner.auth.validators.Rule;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import static com.bluebox.planner.auth.common.Constants.*;
import static com.bluebox.planner.auth.validators.RuleFactory.nullEmptyLenRule;
import static com.bluebox.planner.auth.validators.RuleFactory.nullRule;
import static java.text.MessageFormat.format;

/**
 * @author by kamran ghiasvand
 */
@Service
public class RoleValidationFactory extends ValidationFactory<RoleDto, Long> {

    @Autowired
    public RoleValidationFactory(InputProperties inputProperties) {
        super(inputProperties);

    }

    @Override
    protected void init() {
        var name = nullEmptyLenRule(RoleDto::getName, FIELD_ROLE_NAME, inputProps.getTextMaxLength());
        Rule<RoleDto> nullPermissionId = roleDto -> {
            if (Collections.isEmpty(roleDto.getPermissions()))
                return null;
            List<PermissionDto> permissions = roleDto.getPermissions();
            for (int i = 0; i < permissions.size(); i++) {
                PermissionDto permission = permissions.get(i);
                if (permission.getId() == null)
                    return single(format(FIELD_ROLE_PERMISSION_ID_IS_NULL_MSG, i));
            }
            return null;
        };
        createCtx.addAll(name.getNullRule(), name.getEmptyRule(), name.getLenRule(),
                nullPermissionId);
        updateCtx.addAll(name.getEmptyRule(), name.getLenRule(),
                nullPermissionId);
    }

    private List<String> single(String mess) {
        return java.util.Collections.singletonList(mess);
    }
}
