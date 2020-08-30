package com.bluebox.security.authenticationserver.api.controller.permission;

import com.bluebox.security.authenticationserver.api.controller.permission.dto.*;
import com.bluebox.security.authenticationserver.common.PathConstant;
import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.persistence.entity.PermissionEntity;
import com.bluebox.security.authenticationserver.persistence.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.bluebox.security.authenticationserver.common.Constants.PERMISSION;

/**
 * @author by kamran ghiasvand
 */
@RestController
@RequestMapping(path = PathConstant.PERMISSION_BASE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class PermissionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);

    private final PermissionValidationFactory factory;
    private final PermissionService service;
    private final PermissionMapper mapper;

    @Autowired
    public PermissionController(PermissionValidationFactory factory, PermissionService service, PermissionMapper mapper) {
        this.factory = factory;
        this.service = service;
        this.mapper = mapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    public CreatePermissionResp post(@RequestBody CreatePermissionReq dto) {
        LOGGER.info("Creating {}", PERMISSION);
        factory.createCtx.validate(dto);
        PermissionEntity entity = mapper.createReqToEntity(dto);
        entity = service.create(entity);
        LOGGER.info("{} created", PERMISSION);
        return mapper.entityToCreateResp(entity);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public UpdatePermissionResp put(@RequestBody UpdatePermissionReq dto) throws GlobalException {
        LOGGER.info("Updating {} '{}'", PERMISSION, dto);
        factory.updateCtx.validate(dto);
        var entity = mapper.updateReqToEntity(dto);
        entity = service.update(entity);
        LOGGER.info("{} with id '{}' updated", PERMISSION, entity.getId());
        return mapper.entityToUpdateResp(entity);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public DeletePermissionResp delete(@RequestBody DeletePermissionReq dto) throws GlobalException {
        LOGGER.info("Deleting {} '{}'", PERMISSION, dto);
        factory.deleteCtx.validate(dto);
        var entity = mapper.deleteReqToEntity(dto);
        entity = service.remove(entity);
        LOGGER.info("{} with id '{}' deleted", PERMISSION, entity.getId());
        return mapper.entityToDeleteResp(entity);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public GetPermissionResp get(@PathVariable("id") Long id) throws GlobalException {
        LOGGER.info("Fetching {} with id '{}'", PERMISSION, id);
        var fetch = service.fetch(id);
        return mapper.entityToGetResp(fetch);
    }

}
