package com.bluebox.planner.auth.rest.controller.role;

import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.util.ConvertUtil;
import com.bluebox.planner.auth.common.viewModel.permission.PermissionDto;
import com.bluebox.planner.auth.common.viewModel.role.RoleDto;
import com.bluebox.planner.auth.persistence.entity.PermissionEntity;
import com.bluebox.planner.auth.persistence.entity.regular.RegularRoleEntity;
import com.bluebox.planner.auth.rest.Converter;
import org.springframework.stereotype.Service;

/**
 * @author by kamran ghiasvand
 */
@Service
public class RoleConverter implements Converter<RegularRoleEntity, RoleDto,Long> {
    @Override
    public RegularRoleEntity convert(RoleDto dto, Object... args) throws GlobalException {
        return ConvertUtil.to(dto, RegularRoleEntity.class);
    }

    @Override
    public RoleDto convert(RegularRoleEntity entity, Object... args) throws GlobalException {
        return ConvertUtil.to(entity,RoleDto.class);
    }
}
