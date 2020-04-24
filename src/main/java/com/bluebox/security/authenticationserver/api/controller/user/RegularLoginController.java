package com.bluebox.security.authenticationserver.api.controller.user;//package com.bluebox.security.authenticationserver.rest.controller.regular;
//
//import com.bluebox.security.authenticationserver.common.PathConstant;
//import com.bluebox.security.authenticationserver.common.exception.GlobalException;
//import com.bluebox.security.authenticationserver.common.viewModel.regular.RegularUserDto;
//import com.bluebox.security.authenticationserver.common.viewModel.views.ViewRegularUser;
//import com.bluebox.security.authenticationserver.persistence.service.SecurityService;
//import com.fasterxml.jackson.annotation.JsonView;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author by kamran ghiasvand
// */
//@RestController
//@RequestMapping(PathConstant.LOGIN_BASE)
//public class RegularLoginController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(RegularLoginController.class);
//    private final SecurityService securityService;
//
//    @Autowired
//    public RegularLoginController(SecurityService securityService) {
//
//        this.securityService = securityService;
//    }
//
//    @JsonView(ViewRegularUser.Response.class)
//    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> login(@JsonView(ViewRegularUser.LoginRequest.class) @RequestBody RegularUserDto dto) throws GlobalException {
//        return ResponseEntity.ok(securityService.login(dto.getEmail(), dto.getPassword()));
//
//    }
//
//
//}
