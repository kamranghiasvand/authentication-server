package com.bluebox.planner.auth.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
