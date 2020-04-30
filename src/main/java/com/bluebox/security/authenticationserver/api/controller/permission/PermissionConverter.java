package com.bluebox.security.authenticationserver.api.controller.permission;

import com.bluebox.security.authenticationserver.api.Converter;
import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.common.util.ConvertUtil;
import com.bluebox.security.authenticationserver.common.viewModel.permission.PermissionDto;
import com.bluebox.security.authenticationserver.persistence.entity.PermissionEntity;
import org.springframework.stereotype.Service;

/**
 * @author by kamran ghiasvand
 */
@Service
public class PermissionConverter implements Converter<PermissionEntity, PermissionDto, Long> {
    @Override
    public PermissionEntity convert(PermissionDto dto, Object... args) throws GlobalException {
        return ConvertUtil.to(dto, PermissionEntity.class);
    }

    @Override
    public PermissionDto convert(PermissionEntity entity, Object... args) throws GlobalException {
        return ConvertUtil.to(entity, PermissionDto.class);
    }
}
