package com.bluebox.planner.auth.integration.controller.permission;

import com.bluebox.planner.auth.common.PathConstant;
import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.viewModel.permission.PermissionCto;
import com.bluebox.planner.auth.common.viewModel.permission.PermissionDto;
import com.bluebox.planner.auth.common.viewModel.views.ViewPermission;
import com.bluebox.planner.auth.persistence.entity.PermissionEntity;
import com.bluebox.planner.auth.persistence.service.PermissionService;
import com.bluebox.planner.auth.persistence.service.base.CommandService;
import com.bluebox.planner.auth.persistence.service.base.QueryService;
import com.bluebox.planner.auth.persistence.service.base.enums.IDSortFields;
import com.bluebox.planner.auth.integration.Converter;
import com.bluebox.planner.auth.integration.base.BaseCRUDController;
import com.bluebox.planner.auth.integration.validation.ValidationFactory;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.bluebox.planner.auth.common.Constants.PERMISSION;

/**
 * @author by kamran ghiasvand
 */
@RestController
@RequestMapping(PathConstant.PERMISSION_BASE)
public class PermissionController extends BaseCRUDController<PermissionEntity, PermissionDto, PermissionCto, IDSortFields, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);

    private final PermissionValidationFactory validationFactory;
    private final PermissionService permissionService;
    private final PermissionConverter permissionConverter;


    @Autowired
    public PermissionController(PermissionValidationFactory validationFactory, PermissionService permissionService, PermissionConverter permissionConverter) {
        this.validationFactory = validationFactory;
        this.permissionService = permissionService;
        this.permissionConverter = permissionConverter;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(ViewPermission.Response.class)
    public PermissionDto post(@JsonView(ViewPermission.CreateRequest.class) @RequestBody PermissionDto dto) throws GlobalException {
        return add(dto);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(ViewPermission.Response.class)
    public PermissionDto put(@JsonView(ViewPermission.UpdateRequest.class) @RequestBody PermissionDto dto) throws GlobalException {
        return edit(dto);
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(ViewPermission.Response.class)
    public PermissionDto delete(@RequestParam("id") Long id) throws GlobalException {
        return remove(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(ViewPermission.Response.class)
    public PermissionDto get(@RequestParam("id") Long id) throws GlobalException {
        return fetch(id);
    }

    @Override
    protected Converter<PermissionEntity, PermissionDto, Long> getConverter() {
        return permissionConverter;
    }

    @Override
    protected CommandService<PermissionEntity, Long> getCommandService() {
        return permissionService;
    }

    @Override
    protected ValidationFactory<PermissionDto, Long> getValidationFactory() {
        return validationFactory;
    }

    @Override
    protected QueryService<PermissionEntity, PermissionCto, IDSortFields, Long> getQueryService() {
        return permissionService;
    }

    @Override
    protected String getEntityLabel() {
        return PERMISSION;
    }

    @Override
    protected Class<PermissionDto> getDTOClass() {
        return PermissionDto.class;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
