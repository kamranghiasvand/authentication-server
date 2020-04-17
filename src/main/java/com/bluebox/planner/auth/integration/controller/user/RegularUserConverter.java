package com.bluebox.planner.auth.integration.controller.user;

import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.util.ConvertUtil;
import com.bluebox.planner.auth.common.viewModel.regular.RUserDto;
import com.bluebox.planner.auth.persistence.entity.regular.RegularUserEntity;
import com.bluebox.planner.auth.integration.Converter;
import org.springframework.stereotype.Service;

/**
 * @author by kamran ghiasvand
 */
@Service
public class RegularUserConverter implements Converter<RegularUserEntity , RUserDto,Long> {
    @Override
    public RegularUserEntity convert(RUserDto dto, Object... args) throws GlobalException {
        return ConvertUtil.to(dto, RegularUserEntity.class);
    }

    @Override
    public RUserDto convert(RegularUserEntity entity, Object... args) throws GlobalException {
        return ConvertUtil.to(entity, RUserDto.class);
    }
}
