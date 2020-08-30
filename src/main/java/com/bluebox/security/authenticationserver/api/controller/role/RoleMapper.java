package com.bluebox.security.authenticationserver.api.controller.role;

import com.bluebox.security.authenticationserver.api.controller.role.dto.*;
import com.bluebox.security.authenticationserver.persistence.entity.BaseEntity;
import com.bluebox.security.authenticationserver.persistence.entity.PermissionEntity;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Kamran Ghiasvand
 */
@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "domain", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "permissions", expression = "java(this.getPermissionEntityList(dto.getPermissions()))")
    RoleEntity createReqToEntity(CreateRoleReq dto);

    @Mapping(target = "permissions", expression = "java(this.getPermissionIdList(entity.getPermissions()))")
    CreateRoleResp entityToCreateResp(RoleEntity entity);

    default List<PermissionEntity> getPermissionEntityList(List<Long> ids) {
        if (ids == null)
            return null;
        return ids.stream().filter(Objects::nonNull).map(PermissionEntity::new).collect(Collectors.toCollection(ArrayList::new));
    }

    default List<Long> getPermissionIdList(List<PermissionEntity> entities) {
        if (entities == null)
            return null;
        return entities.stream().filter(Objects::nonNull).map(BaseEntity::getId).collect(Collectors.toCollection(ArrayList::new));
    }

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "domain", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "permissions", expression = "java(this.getPermissionEntityList(dto.getPermissions()))")
    RoleEntity updateReqToEntity(UpdateRoleReq dto);

    @Mapping(target = "permissions", expression = "java(this.getPermissionIdList(entity.getPermissions()))")
    UpdateRoleResp entityToUpdateResp(RoleEntity entity);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "domain", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    RoleEntity deleteReqToEntity(DeleteRoleReq dto);

    DeleteRoleResp entityToDeleteResp(RoleEntity entity);

    @Mapping(target = "permissions", expression = "java(this.getPermissionIdList(entity.getPermissions()))")
    GetRoleResp entityToGetResp(RoleEntity entity);
}
