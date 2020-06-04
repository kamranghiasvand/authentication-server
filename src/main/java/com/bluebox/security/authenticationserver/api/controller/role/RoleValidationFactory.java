package com.bluebox.security.authenticationserver.api.controller.role;

import com.bluebox.security.authenticationserver.api.validation.DtoValidationFactory;
import com.bluebox.security.authenticationserver.common.config.InputProperties;
import com.bluebox.security.authenticationserver.common.viewModel.permission.PermissionDto;
import com.bluebox.security.authenticationserver.common.viewModel.role.RoleDto;
import com.bluebox.security.authenticationserver.validators.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.bluebox.security.authenticationserver.common.Constants.FIELD_ROLE_NAME;
import static com.bluebox.security.authenticationserver.common.Constants.FIELD_ROLE_PERMISSION_ID_IS_NULL_MSG;
import static com.bluebox.security.authenticationserver.validators.RuleFactory.nullEmptyLenRule;
import static java.text.MessageFormat.format;


/**
 * @author by kamran ghiasvand
 */
@Service
public class RoleValidationFactory extends DtoValidationFactory<RoleDto, Long> {

    @Autowired
    public RoleValidationFactory(InputProperties inputProperties) {
        super(inputProperties);

    }

    @Override
    protected void init() {
        var name = nullEmptyLenRule(RoleDto::getName, FIELD_ROLE_NAME, inputProps.getTextMaxLength());
        Rule<RoleDto> nullPermissionId = roleDto -> {
            if (CollectionUtils.isEmpty(roleDto.getPermissions()))
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
