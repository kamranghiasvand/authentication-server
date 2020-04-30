package com.bluebox.security.authenticationserver.persistence.service;

import com.bluebox.security.authenticationserver.common.Constants;
import com.bluebox.security.authenticationserver.common.viewModel.regular.RUserCto;
import com.bluebox.security.authenticationserver.persistence.entity.regular.RegularUserEntity;
import com.bluebox.security.authenticationserver.persistence.repository.BaseRepository;
import com.bluebox.security.authenticationserver.persistence.repository.RegularUserRepository;
import com.bluebox.security.authenticationserver.persistence.service.base.AbstractCRUDService;
import com.bluebox.security.authenticationserver.persistence.service.base.BaseSpec;
import com.bluebox.security.authenticationserver.persistence.service.base.enums.IDSortFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author by kamran ghiasvand
 */
@Service
public class RegularUserService extends AbstractCRUDService<RegularUserEntity, RUserCto, IDSortFields, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegularUserService.class);
    private final RegularUserRepository regularUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegularUserService(RegularUserRepository regularUserRepository, PasswordEncoder passwordEncoder) {
        this.regularUserRepository = regularUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegularUserEntity create(RegularUserEntity entity) {
        if (entity.getPassword() != null)
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return super.create(entity);
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
    protected BaseSpec<RegularUserEntity> getSpec(RUserCto criteria) {
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
