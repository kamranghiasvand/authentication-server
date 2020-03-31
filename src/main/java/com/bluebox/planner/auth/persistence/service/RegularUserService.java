package com.bluebox.planner.auth.persistence.service;

import com.bluebox.planner.auth.common.Constants;
import com.bluebox.planner.auth.common.viewModel.BaseCto;
import com.bluebox.planner.auth.common.viewModel.regular.RegularUserCto;
import com.bluebox.planner.auth.persistence.entity.regular.RegularUserEntity;
import com.bluebox.planner.auth.persistence.repository.BaseRepository;
import com.bluebox.planner.auth.persistence.repository.RegularUserRepository;
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
public class RegularUserService extends AbstractCRUDService<RegularUserEntity, RegularUserCto, IDSortFields,Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegularUserService.class);
    private final RegularUserRepository regularUserRepository;

    @Autowired
    public RegularUserService(RegularUserRepository regularUserRepository) {
        this.regularUserRepository = regularUserRepository;
    }


    @Override
    protected Class<RegularUserEntity> getEntityClass() {
        return RegularUserEntity.class;
    }

    @Override
    protected void edit(RegularUserEntity foundedInDB, RegularUserEntity newEntity) {
        LOGGER.info("editing entity");
    }

    @Override
    protected BaseSpec<RegularUserEntity> getSpec(RegularUserCto criteria) {
        return new BaseSpec<>();
    }

    @Override
    public BaseRepository<RegularUserEntity, Long> getRepository() {
        return regularUserRepository;
    }

    @Override
    public String getEntityName() {
        return Constants.USER_SERVICE;
    }
}
