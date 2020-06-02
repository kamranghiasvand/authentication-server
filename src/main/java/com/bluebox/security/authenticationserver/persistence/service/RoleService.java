package com.bluebox.security.authenticationserver.persistence.service;

import com.bluebox.security.authenticationserver.common.Constants;
import com.bluebox.security.authenticationserver.common.viewModel.role.RoleCto;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RoleEntity;
import com.bluebox.security.authenticationserver.persistence.repository.BaseRepository;
import com.bluebox.security.authenticationserver.persistence.repository.RoleRepository;
import com.bluebox.security.authenticationserver.persistence.service.base.AbstractCRUDService;
import com.bluebox.security.authenticationserver.persistence.service.base.BaseSpec;
import com.bluebox.security.authenticationserver.persistence.service.base.enums.IDSortFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author by kamran ghiasvand
 */
@Service
@Transactional
public class RoleService extends AbstractCRUDService<RoleEntity, RoleCto, IDSortFields, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    protected Class<RoleEntity> getEntityClass() {
        return RoleEntity.class;
    }

    @Override
    protected void edit(RoleEntity foundedInDB, RoleEntity newEntity) {
        LOGGER.info("editing entity");
    }


    @Override
    protected BaseSpec<RoleEntity> getSpec(RoleCto criteria) {
        return new BaseSpec<>();
    }


    @Override
    public BaseRepository<RoleEntity, Long> getRepository() {
        return roleRepository;
    }

    @Override
    public String getEntityName() {
        return Constants.ROLE_SERVICE;
    }


}
