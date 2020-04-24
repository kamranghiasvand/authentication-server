package com.bluebox.security.authenticationserver.api.controller.role;

import com.bluebox.security.authenticationserver.api.Converter;
import com.bluebox.security.authenticationserver.api.base.BaseCRUDController;
import com.bluebox.security.authenticationserver.api.validation.ValidationFactory;
import com.bluebox.security.authenticationserver.common.PathConstant;
import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.common.viewModel.role.RoleCto;
import com.bluebox.security.authenticationserver.common.viewModel.role.RoleDto;
import com.bluebox.security.authenticationserver.common.viewModel.views.ViewRole;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RoleEntity;
import com.bluebox.security.authenticationserver.persistence.service.RoleService;
import com.bluebox.security.authenticationserver.persistence.service.base.CommandService;
import com.bluebox.security.authenticationserver.persistence.service.base.QueryService;
import com.bluebox.security.authenticationserver.persistence.service.base.enums.IDSortFields;
import com.fasterxml.jackson.annotation.JsonView;
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
@RequestMapping(PathConstant.ROLE_BASE)
public class RoleController extends BaseCRUDController<RoleEntity, RoleDto, RoleCto, IDSortFields, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    private final RoleValidationFactory validationFactory;
    private final RoleService roleService;
    private final RoleConverter roleConverter;


    @Autowired
    public RoleController(RoleValidationFactory validationFactory, RoleService roleService, RoleConverter roleConverter) {
        this.validationFactory = validationFactory;
        this.roleService = roleService;
        this.roleConverter = roleConverter;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(ViewRole.Response.class)
    public RoleDto post(@JsonView(ViewRole.CreateRequest.class) @RequestBody RoleDto dto) throws GlobalException {
        return add(dto);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(ViewRole.Response.class)
    public RoleDto put(@JsonView(ViewRole.UpdateRequest.class) @RequestBody RoleDto dto) throws GlobalException {
        return edit(dto);
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(ViewRole.Response.class)
    public RoleDto delete(@RequestParam("id") Long id) throws GlobalException {
        return remove(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(ViewRole.Response.class)
    public RoleDto get(@RequestParam("id") Long id) throws GlobalException {
        return fetch(id);
    }



    @Override
    protected Converter<RoleEntity, RoleDto, Long> getConverter() {
        return roleConverter;
    }

    @Override
    protected CommandService<RoleEntity, Long> getCommandService() {
        return roleService;
    }

    @Override
    protected ValidationFactory<RoleDto, Long> getValidationFactory() {
        return validationFactory;
    }

    @Override
    protected QueryService<RoleEntity, RoleCto, IDSortFields, Long> getQueryService() {
        return roleService;
    }

    @Override
    protected String getEntityLabel() {
        return ROLE;
    }

    @Override
    protected Class<RoleDto> getDTOClass() {
        return RoleDto.class;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
