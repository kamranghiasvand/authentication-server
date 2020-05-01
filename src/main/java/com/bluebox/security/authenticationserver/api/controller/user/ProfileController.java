package com.bluebox.security.authenticationserver.api.controller.user;

import com.bluebox.security.authenticationserver.api.Converter;
import com.bluebox.security.authenticationserver.api.base.BaseQueryController;
import com.bluebox.security.authenticationserver.common.PathConstant;
import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.common.viewModel.regular.RUserCto;
import com.bluebox.security.authenticationserver.common.viewModel.regular.RUserDto;
import com.bluebox.security.authenticationserver.common.viewModel.views.ViewRegularUser;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RegularUserEntity;
import com.bluebox.security.authenticationserver.persistence.service.RegularUserService;
import com.bluebox.security.authenticationserver.persistence.service.base.QueryService;
import com.bluebox.security.authenticationserver.persistence.service.base.enums.IDSortFields;
import com.bluebox.security.authenticationserver.security.UserPrincipal;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.bluebox.security.authenticationserver.common.Constants.REGULAR_USER;

/**
 * @author by kamran ghiasvand
 */
@RestController
@RequestMapping(PathConstant.PROFILE_BASE)
public class ProfileController extends BaseQueryController<RegularUserEntity, RUserDto, RUserCto, IDSortFields, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);
    private final RegularUserService userService;
    private final UserConverter converter;

    @Autowired
    public ProfileController(RegularUserService userService, UserConverter converter) {
        this.userService = userService;
        this.converter = converter;
    }

    @JsonView(ViewRegularUser.Response.class)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RUserDto profile() throws GlobalException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = (UserPrincipal) authentication.getPrincipal();
        return converter.convert(userService.fetch(principal.getId()));
    }


    @Override
    protected Converter<RegularUserEntity, RUserDto, Long> getConverter() {
        return converter;
    }

    @Override
    protected QueryService<RegularUserEntity, RUserCto, IDSortFields, Long> getQueryService() {
        return userService;
    }

    @Override
    protected String getEntityLabel() {
        return REGULAR_USER;
    }

    @Override
    protected Class<RUserDto> getDTOClass() {
        return RUserDto.class;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
