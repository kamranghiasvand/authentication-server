package com.bluebox.planner.auth.integration.controller.role;

import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.util.ConvertUtil;
import com.bluebox.planner.auth.common.viewModel.role.RoleDto;
import com.bluebox.planner.auth.persistence.entity.regular.RoleEntity;
import com.bluebox.planner.auth.integration.Converter;
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
