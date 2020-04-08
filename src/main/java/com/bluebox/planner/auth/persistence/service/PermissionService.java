package com.bluebox.planner.auth.persistence.service;

import com.bluebox.planner.auth.common.Constants;
import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.viewModel.permission.PermissionCto;
import com.bluebox.planner.auth.common.viewModel.permission.PermissionDto;
import com.bluebox.planner.auth.persistence.entity.PermissionEntity;
import com.bluebox.planner.auth.persistence.repository.BaseRepository;
import com.bluebox.planner.auth.persistence.repository.PermissionRepository;
import com.bluebox.planner.auth.persistence.repository.RegularUserRepository;
import com.bluebox.planner.auth.persistence.service.base.AbstractCRUDService;
import com.bluebox.planner.auth.persistence.service.base.BaseSpec;
import com.bluebox.planner.auth.persistence.service.base.enums.IDSortFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
