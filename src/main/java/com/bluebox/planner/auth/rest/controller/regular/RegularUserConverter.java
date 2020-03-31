package com.bluebox.planner.auth.rest.controller.regular;

import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.util.ConvertUtil;
import com.bluebox.planner.auth.common.viewModel.regular.RegularUserDto;
import com.bluebox.planner.auth.persistence.entity.regular.RegularUserEntity;
import com.bluebox.planner.auth.rest.Converter;
import org.springframework.stereotype.Service;

/**
 * @author by kamran ghiasvand
 */
@Service
public class RegularUserConverter implements Converter<RegularUserEntity , RegularUserDto,Long> {
    @Override
    public RegularUserEntity convert(RegularUserDto dto, Object... args) throws GlobalException {
        return ConvertUtil.to(dto, RegularUserEntity.class);
    }

    @Override
    public RegularUserDto convert(RegularUserEntity entity, Object... args) throws GlobalException {
        return ConvertUtil.to(entity,RegularUserDto.class);
    }
}
