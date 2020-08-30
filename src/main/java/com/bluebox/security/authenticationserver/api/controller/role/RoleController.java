package com.bluebox.security.authenticationserver.api.controller.role;

import com.bluebox.security.authenticationserver.api.controller.role.dto.*;
import com.bluebox.security.authenticationserver.common.PathConstant;
import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RoleEntity;
import com.bluebox.security.authenticationserver.persistence.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.bluebox.security.authenticationserver.common.Constants.ROLE;

/**
 * @author by kamran ghiasvand
 */
@RestController
@RequestMapping(path = PathConstant.ROLE_BASE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    private final RoleValidationFactory factory;
    private final RoleService service;
    private final RoleMapper mapper;


    @Autowired
    public RoleController(RoleValidationFactory factory, RoleService service, RoleMapper mapper) {
        this.factory = factory;
        this.service = service;
        this.mapper = mapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    public CreateRoleResp post(@RequestBody CreateRoleReq dto) {
        LOGGER.info("Creating {}", ROLE);
        factory.createCtx.validate(dto);
        RoleEntity entity = mapper.createReqToEntity(dto);
        entity = service.create(entity);
        LOGGER.info("{} created", ROLE);
        return mapper.entityToCreateResp(entity);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public UpdateRoleResp put(@RequestBody UpdateRoleReq dto) {
        LOGGER.info("Updating {} '{}'", ROLE, dto);
        factory.updateCtx.validate(dto);
        var entity = mapper.updateReqToEntity(dto);
        entity = service.update(entity);
        LOGGER.info("{} with id '{}' updated", ROLE, entity.getId());
        return mapper.entityToUpdateResp(entity);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public DeleteRoleResp delete(@RequestBody DeleteRoleReq dto) throws GlobalException {
        LOGGER.info("Deleting {} '{}'", ROLE, dto);
        factory.deleteCtx.validate(dto);
        var entity = mapper.deleteReqToEntity(dto);
        entity = service.remove(entity);
        LOGGER.info("{} with id '{}' deleted", ROLE, entity.getId());
        return mapper.entityToDeleteResp(entity);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public GetRoleResp get(@PathVariable("id") Long id) throws GlobalException {
        LOGGER.info("Fetching {} with id '{}'", ROLE, id);
        final var entity = service.fetch(id);
        return mapper.entityToGetResp(entity);
    }
}
