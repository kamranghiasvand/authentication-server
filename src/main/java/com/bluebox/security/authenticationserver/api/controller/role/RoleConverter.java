package com.bluebox.security.authenticationserver.api.controller.role;

import com.bluebox.security.authenticationserver.api.Converter;
import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.common.util.ConvertUtil;
import com.bluebox.security.authenticationserver.common.viewModel.role.RoleDto;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RoleEntity;
import org.springframework.stereotype.Service;

/**
 * @author by kamran ghiasvand
 */
@Service
public class RoleConverter implements Converter<RoleEntity, RoleDto,Long> {
    @Override
    public RoleEntity convert(RoleDto dto, Object... args) throws GlobalException {
        return ConvertUtil.to(dto, RoleEntity.class);
    }

    @Override
    public RoleDto convert(RoleEntity entity, Object... args) throws GlobalException {
        return ConvertUtil.to(entity,RoleDto.class);
    }
}
