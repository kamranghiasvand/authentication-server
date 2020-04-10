package com.bluebox.planner.auth.persistence.service;

import com.bluebox.planner.auth.common.Constants;
import com.bluebox.planner.auth.common.config.PhoneVerificationConfig;
import com.bluebox.planner.auth.common.exception.PhoneVerificationException;
import com.bluebox.planner.auth.common.util.RandomString;
import com.bluebox.planner.auth.common.viewModel.BaseCto;
import com.bluebox.planner.auth.persistence.entity.PhoneVerificationEntity;
import com.bluebox.planner.auth.persistence.repository.BaseRepository;
import com.bluebox.planner.auth.persistence.repository.PhoneVerificationRepository;
import com.bluebox.planner.auth.persistence.service.base.AbstractCRUDService;
import com.bluebox.planner.auth.persistence.service.base.BaseSpec;
import com.bluebox.planner.auth.persistence.service.base.enums.IDSortFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 * @author by kamran ghiasvand
 */
@Service
public class PhoneVerificationService extends AbstractCRUDService<PhoneVerificationEntity, BaseCto, IDSortFields, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneVerificationService.class);
    private final PhoneVerificationRepository repository;
    private final SmsService smsService;
    private final PhoneVerificationConfig config;

    @Autowired
    public PhoneVerificationService(PhoneVerificationRepository repository, SmsService smsService, PhoneVerificationConfig config) {
        this.repository = repository;
        this.smsService = smsService;
        this.config = config;
    }

    @Transactional
    public void send(String phoneNumber) {
        var randomString = new RandomString(config.getCodeLen());
        var code = randomString.nextString();
        var entity = createVerificationEntity(phoneNumber, code);
        repository.save(entity);
        smsService.sendText(phoneNumber, code);
    }

    @Transactional
    public Boolean verify(String phoneNumber, String code) throws PhoneVerificationException {
        var result = repository.findFirstByPhoneNumberAndCode(phoneNumber, code);
        if (result.isEmpty())
            throw new PhoneVerificationException("code is not valid");
        var entity = result.get();
        if (codeExpired(entity)) {
            repository.delete(result.get());
            throw new PhoneVerificationException("code is not valid");
        }
        repository.delete(result.get());
        return true;
    }

    private Boolean codeExpired(PhoneVerificationEntity entity) {
        if (entity.getSentTime() == null)
            return true;
        long duration = System.currentTimeMillis() - entity.getSentTime().getTime();
        return TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS) > config.getExpireTime();
    }

    private PhoneVerificationEntity createVerificationEntity(String phoneNumber, String code) {
        var entity = new PhoneVerificationEntity();
        entity.setCode(code);
        entity.setPhoneNumber(phoneNumber);
        entity.setSentTime(new Timestamp(System.currentTimeMillis()));
        return entity;
    }


    @Override
    protected Class<PhoneVerificationEntity> getEntityClass() {
        return PhoneVerificationEntity.class;
    }

    @Override
    protected void edit(PhoneVerificationEntity foundedInDB, PhoneVerificationEntity newEntity) {
        LOGGER.info("editing entity");
    }


    @Override
    protected BaseSpec<PhoneVerificationEntity> getSpec(BaseCto criteria) {
        return new BaseSpec<>();
    }


    @Override
    public BaseRepository<PhoneVerificationEntity, Long> getRepository() {
        return repository;
    }

    @Override
    public String getEntityName() {
        return Constants.PHONE_VERIFICATION_SERVICE;
    }


}
