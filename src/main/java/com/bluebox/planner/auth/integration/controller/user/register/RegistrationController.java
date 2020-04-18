package com.bluebox.planner.auth.integration.controller.user.register;

import com.bluebox.planner.auth.common.PathConstant;
import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.util.RandomPassUtil;
import com.bluebox.planner.auth.common.viewModel.regular.RUserCto;
import com.bluebox.planner.auth.common.viewModel.regular.RUserDto;
import com.bluebox.planner.auth.common.viewModel.views.ViewRegularUser;
import com.bluebox.planner.auth.persistence.entity.regular.RegularUserEntity;
import com.bluebox.planner.auth.persistence.service.PhoneVerificationService;
import com.bluebox.planner.auth.persistence.service.RegularUserService;
import com.bluebox.planner.auth.persistence.service.base.CommandService;
import com.bluebox.planner.auth.persistence.service.base.QueryService;
import com.bluebox.planner.auth.persistence.service.base.enums.IDSortFields;
import com.bluebox.planner.auth.integration.Converter;
import com.bluebox.planner.auth.integration.base.BaseCRUDController;
import com.bluebox.planner.auth.integration.controller.user.RegularUserConverter;
import com.bluebox.planner.auth.integration.controller.user.RegularValidationFactory;
import com.bluebox.planner.auth.integration.validation.ValidationFactory;
import com.bluebox.planner.auth.validators.RuleFactory;
import com.bluebox.planner.auth.validators.ValidationContext;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static com.bluebox.planner.auth.common.Constants.FIELD_USER_PHONE;
import static com.bluebox.planner.auth.common.Constants.REGULAR_USER;

/**
 * @author by kamran ghiasvand
 */
@RestController
@RequestMapping(PathConstant.REGISTRATION_BASE)
public class RegistrationController extends BaseCRUDController<RegularUserEntity, RUserDto, RUserCto, IDSortFields, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    private final RegularUserService userService;
    private final RegularValidationFactory validationFactory;
    private final RegularUserConverter converter;
    private final PhoneVerificationService phoneVerificationService;

    @Autowired
    public RegistrationController(RegularUserService userService, RegularValidationFactory validationFactory, RegularUserConverter converter, PhoneVerificationService phoneVerificationService) {
        this.userService = userService;
        this.validationFactory = validationFactory;
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
        if (StringUtils.isEmpty(userDto.getPassword())){
            userDto.setPassword(RandomPassUtil.generatePassword());
            userDto.setMatchingPassword(userDto.getPassword());
        }
        return add(userDto);
    }


    @Override
    protected Converter<RegularUserEntity, RUserDto, Long> getConverter() {
        return converter;
    }

    @Override
    protected CommandService<RegularUserEntity, Long> getCommandService() {
        return userService;
    }

    @Override
    protected ValidationFactory<RUserDto, Long> getValidationFactory() {
        return validationFactory;
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
