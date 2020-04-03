package com.bluebox.planner.auth.rest.controller;

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
import com.bluebox.planner.auth.rest.Converter;
import com.bluebox.planner.auth.rest.base.BaseCRUDController;
import com.bluebox.planner.auth.rest.controller.regular.RegularUserConverter;
import com.bluebox.planner.auth.rest.controller.regular.RegularValidationFactory;
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
@RequestMapping("/api/test")
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);



    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String postTest(){
        LOGGER.info("post test");
    return "POST";
    }
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getTest(){
        LOGGER.info("get test");
        return "GET";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String putTest(){
        LOGGER.info("get test");
        return "GET";
    }



}
