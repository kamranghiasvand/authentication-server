package com.bluebox.planner.auth.persistence.service.base;

import com.bluebox.planner.auth.common.dto.UserDto;
import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.persistence.entity.UserEntity;
import com.bluebox.planner.auth.persistence.repository.BaseRepository;
import com.bluebox.planner.auth.persistence.service.base.enums.IDSortFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author by kamran ghiasvand
 */
public class UserServiceImpl extends AbstractCRUDService<UserEntity, UserDto, IDSortFields,Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    protected Class<UserEntity> getEntityClass() {
        return null;
    }

    @Override
    protected void edit(UserEntity foundedInDB, UserDto dto) throws GlobalException {

    }

    @Override
    public BaseRepository<UserEntity, ?> getRepository() {
        return null;
    }

    @Override
    public String getEntityName() {
        return null;
    }

    @Override
    protected Class<UserDto> getDTOClass() {
        return null;
    }

    @Override
    protected Optional<UserEntity> extract(Long key) {
        return Optional.empty();
    }

    @Override
    public UserDto fetch(Long key) throws GlobalException {
        return null;
    }
}
