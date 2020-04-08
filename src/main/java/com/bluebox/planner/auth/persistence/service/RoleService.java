package com.bluebox.planner.auth.persistence.service;

import com.bluebox.planner.auth.common.Constants;
import com.bluebox.planner.auth.common.viewModel.permission.PermissionCto;
import com.bluebox.planner.auth.common.viewModel.role.RoleCto;
import com.bluebox.planner.auth.persistence.entity.PermissionEntity;
import com.bluebox.planner.auth.persistence.entity.regular.RegularRoleEntity;
import com.bluebox.planner.auth.persistence.repository.BaseRepository;
import com.bluebox.planner.auth.persistence.repository.PermissionRepository;
import com.bluebox.planner.auth.persistence.repository.RoleRepository;
import com.bluebox.planner.auth.persistence.service.base.AbstractCRUDService;
import com.bluebox.planner.auth.persistence.service.base.BaseSpec;
import com.bluebox.planner.auth.persistence.service.base.enums.IDSortFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by kamran ghiasvand
 */
@Service
public class RoleService extends AbstractCRUDService<RegularRoleEntity, RoleCto, IDSortFields, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    protected Class<RegularRoleEntity> getEntityClass() {
        return RegularRoleEntity.class;
    }

    @Override
    protected void edit(RegularRoleEntity foundedInDB, RegularRoleEntity newEntity) {
        LOGGER.info("editing entity");
    }


    @Override
    protected BaseSpec<RegularRoleEntity> getSpec(RoleCto criteria) {
        return new BaseSpec<>();
    }


    @Override
    public BaseRepository<RegularRoleEntity, Long> getRepository() {
        return roleRepository;
    }

    @Override
    public String getEntityName() {
        return Constants.ROLE_SERVICE;
    }


}
