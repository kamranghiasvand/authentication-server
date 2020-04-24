package com.bluebox.security.authenticationserver.persistence.service;

import com.bluebox.security.authenticationserver.common.Constants;
import com.bluebox.security.authenticationserver.common.viewModel.permission.PermissionCto;
import com.bluebox.security.authenticationserver.persistence.entity.PermissionEntity;
import com.bluebox.security.authenticationserver.persistence.repository.BaseRepository;
import com.bluebox.security.authenticationserver.persistence.repository.PermissionRepository;
import com.bluebox.security.authenticationserver.persistence.service.base.AbstractCRUDService;
import com.bluebox.security.authenticationserver.persistence.service.base.BaseSpec;
import com.bluebox.security.authenticationserver.persistence.service.base.enums.IDSortFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by kamran ghiasvand
 */
@Service
public class PermissionService extends AbstractCRUDService<PermissionEntity, PermissionCto, IDSortFields, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionService.class);
    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }


    @Override
    protected Class<PermissionEntity> getEntityClass() {
        return PermissionEntity.class;
    }

    @Override
    protected void edit(PermissionEntity foundedInDB, PermissionEntity newEntity) {
        LOGGER.info("editing entity");
    }


    @Override
    protected BaseSpec<PermissionEntity> getSpec(PermissionCto criteria) {
        return new BaseSpec<>();
    }


    @Override
    public BaseRepository<PermissionEntity, Long> getRepository() {
        return permissionRepository;
    }

    @Override
    public String getEntityName() {
        return Constants.PERMISSION_SERVICE;
    }


}
