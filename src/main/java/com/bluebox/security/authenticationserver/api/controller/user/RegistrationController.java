package com.bluebox.security.authenticationserver.api.controller.user;

import com.bluebox.security.authenticationserver.api.Converter;
import com.bluebox.security.authenticationserver.api.base.BaseCRUDController;
import com.bluebox.security.authenticationserver.api.validation.DtoValidationFactory;
import com.bluebox.security.authenticationserver.common.PathConstant;
import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.common.viewModel.regular.RUserCto;
import com.bluebox.security.authenticationserver.common.viewModel.regular.RUserDto;
import com.bluebox.security.authenticationserver.common.viewModel.views.ViewRegularUser;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RegularUserEntity;
import com.bluebox.security.authenticationserver.persistence.service.PhoneVerificationService;
import com.bluebox.security.authenticationserver.persistence.service.RegularUserService;
import com.bluebox.security.authenticationserver.persistence.service.base.CommandService;
import com.bluebox.security.authenticationserver.persistence.service.base.QueryService;
import com.bluebox.security.authenticationserver.persistence.service.base.enums.IDSortFields;
import com.bluebox.security.authenticationserver.validators.RuleFactory;
import com.bluebox.security.authenticationserver.validators.ValidationContext;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bluebox.security.authenticationserver.common.Constants.FIELD_USER_PHONE;
import static com.bluebox.security.authenticationserver.common.Constants.REGULAR_USER;

/**
 * @author by kamran ghiasvand
 */
@RestController
@RequestMapping(PathConstant.REGISTRATION_BASE)
public class RegistrationController extends BaseCRUDController<RegularUserEntity, RUserDto, RUserCto, IDSortFields, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    private final RegularUserService service;
    private final UserValidationFactory factory;
    private final PhoneVerificationService phoneVerificationService;

    @Autowired
    public RegistrationController(RegularUserService service, UserValidationFactory factory, UserConverter converter, PhoneVerificationService phoneVerificationService) {
        this.service = service;
        this.factory = factory;
        this.converter = converter;
        this.phoneVerificationService = phoneVerificationService;
    }

    @JsonView(ViewRegularUser.Response.class)
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RUserDto register(@JsonView(ViewRegularUser.CreateRequest.class) @RequestBody RUserDto dto) throws GlobalException {
        return add(dto);
    }


    @RequestMapping(value = PathConstant.SEND_PHONE_VERIFICATION_CODE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendPhoneVerificationCode(@RequestParam("phone") String phone) throws GlobalException {
        var validationContext = new ValidationContext<String>(RuleFactory.validPhone(s -> s, FIELD_USER_PHONE));
        validationContext.validate(phone);
        phoneVerificationService.send(phone);
        return ResponseEntity.noContent().build();
    }

    @JsonView(ViewRegularUser.Response.class)
    @RequestMapping(value = PathConstant.REGISTER_WITH_PHONE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RUserDto registerWithPhoneVerificationCode(@RequestParam("code") String code, @RequestBody RUserDto userDto) throws GlobalException {
        phoneVerificationService.verify(userDto.getPhone(), code);
        userDto.setEnabled(true);
        return add(userDto);
    }


    @Override
    protected Converter<RegularUserEntity, RUserDto, Long> getConverter() {
        return converter;
    }

    @Override
    protected CommandService<RegularUserEntity, Long> getCommandService() {
        return service;
    }

    @Override
    protected DtoValidationFactory<RUserDto, Long> getFactory() {
        return factory;
    }

    @Override
    protected QueryService<RegularUserEntity, RUserCto, IDSortFields, Long> getQueryService() {
        return service;
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
