package com.bluebox.security.authenticationserver.api.controller.user;

import com.bluebox.security.authenticationserver.api.Converter;
import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.common.util.ConvertUtil;
import com.bluebox.security.authenticationserver.common.viewModel.regular.RUserDto;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RegularUserEntity;
import org.springframework.stereotype.Service;

/**
 * @author by kamran ghiasvand
 */
@Service
public class UserConverter implements Converter<RegularUserEntity, RUserDto, Long> {
    @Override
    public RegularUserEntity convert(RUserDto dto, Object... args) throws GlobalException {
        return ConvertUtil.to(dto, RegularUserEntity.class);
    }

    @Override
    public RUserDto convert(RegularUserEntity entity, Object... args) throws GlobalException {
        return ConvertUtil.to(entity, RUserDto.class);
    }
}
