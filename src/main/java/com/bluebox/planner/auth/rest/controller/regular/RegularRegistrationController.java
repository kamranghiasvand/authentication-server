package com.bluebox.planner.auth.rest.controller.regular;

import com.bluebox.planner.auth.common.PathConstant;
import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.exception.NotConverterSupportException;
import com.bluebox.planner.auth.common.viewModel.regular.RegularUserCto;
import com.bluebox.planner.auth.common.viewModel.regular.RegularUserDto;
import com.bluebox.planner.auth.common.viewModel.views.ViewRegularUser;
import com.bluebox.planner.auth.persistence.entity.regular.RegularUserEntity;
import com.bluebox.planner.auth.persistence.service.RegularUserService;
import com.bluebox.planner.auth.persistence.service.base.CommandService;
import com.bluebox.planner.auth.persistence.service.base.QueryService;
import com.bluebox.planner.auth.persistence.service.base.enums.IDSortFields;
import com.bluebox.planner.auth.rest.base.BaseCRUDController;
import com.bluebox.planner.auth.rest.Converter;
import com.bluebox.planner.auth.rest.validation.ValidationFactory;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.bluebox.planner.auth.common.Constants.REGULAR_USER;

/**
 * @author by kamran ghiasvand
 */
@RestController
@RequestMapping(PathConstant.REGISTRATION_BASE)
public class RegularRegistrationController extends BaseCRUDController<RegularUserEntity, RegularUserDto, RegularUserCto, IDSortFields,Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegularRegistrationController.class);
    private final RegularUserService userService;
    private final RegularValidationFactory validationFactory;
    private final RegularUserConverter converter;

    @Autowired
    public RegularRegistrationController(RegularUserService userService, RegularValidationFactory validationFactory, RegularUserConverter converter) {
        this.userService = userService;
        this.validationFactory = validationFactory;
        this.converter = converter;
    }

    @JsonView(ViewRegularUser.Response.class)
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RegularUserDto register(@JsonView(ViewRegularUser.CreateRequest.class) @RequestBody RegularUserDto dto) throws GlobalException {
        return add(dto);
    }




    @Override
    protected Converter<RegularUserEntity, RegularUserDto, Long> getConverter()  {
        return converter;
    }

    @Override
    protected CommandService<RegularUserEntity, Long> getCommandService() {
        return userService;
    }

    @Override
    protected ValidationFactory<RegularUserDto, Long> getValidationFactory() {
        return validationFactory;
    }

    @Override
    protected QueryService<RegularUserEntity, RegularUserCto, IDSortFields, Long> getQueryService() {
        return userService;
    }

    @Override
    protected String getEntityLabel() {
        return REGULAR_USER;
    }

    @Override
    protected Class<RegularUserDto> getDTOClass() {
        return RegularUserDto.class;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
