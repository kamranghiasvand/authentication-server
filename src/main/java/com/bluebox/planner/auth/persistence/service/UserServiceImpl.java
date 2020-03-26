package com.bluebox.planner.auth.persistence.service;

import com.bluebox.planner.auth.common.Constants;
import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.viewModel.cto.BaseCto;
import com.bluebox.planner.auth.persistence.entity.UserEntity;
import com.bluebox.planner.auth.persistence.repository.BaseRepository;
import com.bluebox.planner.auth.persistence.repository.UserRepository;
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
public class UserServiceImpl extends AbstractCRUDService<UserEntity, BaseCto, IDSortFields,Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    protected Class<UserEntity> getEntityClass() {
        return UserEntity.class;
    }

    @Override
    protected void edit(UserEntity foundedInDB, UserEntity newEntity) {
        LOGGER.info("editing entity");
    }

    @Override
    protected BaseSpec<UserEntity> getSpec(BaseCto criteria) {
        return new BaseSpec<>();
    }

    @Override
    public BaseRepository<UserEntity, Long> getRepository() {
        return userRepository;
    }

    @Override
    public String getEntityName() {
        return Constants.USER_SERVICE;
    }
}
