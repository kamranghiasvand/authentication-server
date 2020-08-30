package com.bluebox.security.authenticationserver.api.controller.permission;

import com.bluebox.security.authenticationserver.api.controller.permission.dto.*;
import com.bluebox.security.authenticationserver.persistence.entity.PermissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Kamran Ghiasvand
 */
@Mapper(componentModel = "spring")
public interface PermissionMapper {
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "domain", ignore = true)
    PermissionEntity createReqToEntity(CreatePermissionReq dto);

    CreatePermissionResp entityToCreateResp(PermissionEntity entity);

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "domain", ignore = true)
    PermissionEntity updateReqToEntity(UpdatePermissionReq dto);

    UpdatePermissionResp entityToUpdateResp(PermissionEntity entity);

    DeletePermissionResp entityToDeleteResp(PermissionEntity entity);

    GetPermissionResp entityToGetResp(PermissionEntity fetch);

    @Mapping(target = "url", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "method", ignore = true)
    @Mapping(target = "domain", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    PermissionEntity deleteReqToEntity(DeletePermissionReq dto);
}
