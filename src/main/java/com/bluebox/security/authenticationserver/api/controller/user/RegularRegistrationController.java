package com.bluebox.security.authenticationserver.api.controller.user;//package com.bluebox.security.authenticationserver.rest.controller.user;
//
//import com.bluebox.security.authenticationserver.common.PathConstant;
//import com.bluebox.security.authenticationserver.common.exception.GlobalException;
//import com.bluebox.security.authenticationserver.common.viewModel.regular.RegularUserCto;
//import com.bluebox.security.authenticationserver.common.viewModel.regular.RegularUserDto;
//import com.bluebox.security.authenticationserver.common.viewModel.views.ViewRegularUser;
//import com.bluebox.security.authenticationserver.persistence.entity.regular.RegularUserEntity;
//import com.bluebox.security.authenticationserver.persistence.service.RegularUserService;
//import com.bluebox.security.authenticationserver.persistence.service.base.CommandService;
//import com.bluebox.security.authenticationserver.persistence.service.base.QueryService;
//import com.bluebox.security.authenticationserver.persistence.service.base.enums.IDSortFields;
//import com.bluebox.security.authenticationserver.rest.base.BaseCRUDController;
//import com.bluebox.security.authenticationserver.rest.Converter;
//import com.bluebox.security.authenticationserver.rest.validation.ValidationFactory;
//import com.fasterxml.jackson.annotation.JsonView;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import static com.bluebox.security.authenticationserver.common.Constants.REGULAR_USER;
//
///**
// * @author by kamran ghiasvand
// */
//@RestController
//@RequestMapping(PathConstant.REGISTRATION_BASE)
//public class RegularRegistrationController extends BaseCRUDController<RegularUserEntity, RegularUserDto, RegularUserCto, IDSortFields,Long> {
//    private static final Logger LOGGER = LoggerFactory.getLogger(RegularRegistrationController.class);
//    private final RegularUserService userService;
//    private final RegularValidationFactory validationFactory;
//    private final RegularUserConverter converter;
//
//    @Autowired
//    public RegularRegistrationController(RegularUserService userService, RegularValidationFactory validationFactory, RegularUserConverter converter) {
//        this.userService = userService;
//        this.validationFactory = validationFactory;
//        this.converter = converter;
//    }
//
//    @JsonView(ViewRegularUser.Response.class)
//    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public RegularUserDto register(@JsonView(ViewRegularUser.CreateRequest.class) @RequestBody RegularUserDto dto) throws GlobalException {
//        return add(dto);
//    }
//
//
//
//
//    @Override
//    protected Converter<RegularUserEntity, RegularUserDto, Long> getConverter()  {
//        return converter;
//    }
//
//    @Override
//    protected CommandService<RegularUserEntity, Long> getCommandService() {
//        return userService;
//    }
//
//    @Override
//    protected ValidationFactory<RegularUserDto, Long> getValidationFactory() {
//        return validationFactory;
//    }
//
//    @Override
//    protected QueryService<RegularUserEntity, RegularUserCto, IDSortFields, Long> getQueryService() {
//        return userService;
//    }
//
//    @Override
//    protected String getEntityLabel() {
//        return REGULAR_USER;
//    }
//
//    @Override
//    protected Class<RegularUserDto> getDTOClass() {
//        return RegularUserDto.class;
//    }
//
//    @Override
//    protected Logger getLogger() {
//        return LOGGER;
//    }
//}
