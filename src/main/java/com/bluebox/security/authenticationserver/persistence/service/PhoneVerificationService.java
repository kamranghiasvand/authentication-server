package com.bluebox.security.authenticationserver.persistence.service;

import com.bluebox.security.authenticationserver.common.Constants;
import com.bluebox.security.authenticationserver.common.config.PhoneVerificationConfig;
import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.common.exception.PhoneVerificationException;
import com.bluebox.security.authenticationserver.common.util.RandomStringGen;
import com.bluebox.security.authenticationserver.common.viewModel.BaseCto;
import com.bluebox.security.authenticationserver.persistence.entity.PhoneVerificationEntity;
import com.bluebox.security.authenticationserver.persistence.repository.BaseRepository;
import com.bluebox.security.authenticationserver.persistence.repository.PhoneVerificationRepository;
import com.bluebox.security.authenticationserver.persistence.service.base.AbstractCRUDService;
import com.bluebox.security.authenticationserver.persistence.service.base.BaseSpec;
import com.bluebox.security.authenticationserver.persistence.service.base.enums.IDSortFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.bluebox.security.authenticationserver.common.Constants.*;

/**
 * @author by kamran ghiasvand
 */
@Service
public class PhoneVerificationService extends AbstractCRUDService<PhoneVerificationEntity, BaseCto, IDSortFields, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneVerificationService.class);
    private final PhoneVerificationRepository repository;
    private final SmsService smsService;
    private final PhoneVerificationConfig config;
    private final RandomStringGen randomStringGen;

    @Autowired
    public PhoneVerificationService(PhoneVerificationRepository repository, SmsService smsService,
                                    PhoneVerificationConfig config, RandomStringGen randomStringGen) {
        this.repository = repository;
        this.smsService = smsService;
        this.config = config;
        this.randomStringGen = randomStringGen;
    }

    @Transactional
    public void send(String phoneNumber) throws GlobalException {
        checkIfAlreadySent(phoneNumber);
        var code = randomStringGen.nextString(config.getCodeLen());
        var entity = createVerificationEntity(phoneNumber, code);
        smsService.sendText(phoneNumber, code);
        repository.save(entity);
    }

    private void checkIfAlreadySent(String phoneNumber) throws PhoneVerificationException {
        Optional<PhoneVerificationEntity> result = repository.findFirstByPhoneNumber(phoneNumber);
        if (result.isEmpty())
            return;
        PhoneVerificationEntity entity = result.get();
        if (codeExpired(entity)) repository.delete(entity);
        else
            throw new PhoneVerificationException(CODE_ALREADY_SENT_MSG);
    }

    @Transactional
    public void verify(String phoneNumber, String code) throws PhoneVerificationException {
        var result = repository.findFirstByPhoneNumberAndCode(phoneNumber, code);
        try {
            if (result.isEmpty())
                throw new PhoneVerificationException(CODE_OR_PHONE_IS_NOT_VALID_MSG);
            var entity = result.get();
            if (codeExpired(entity))
                throw new PhoneVerificationException(CODE_OR_PHONE_IS_NOT_VALID_MSG);
        } finally {
            result.ifPresent(repository::delete);
        }
    }

    private Boolean codeExpired(PhoneVerificationEntity entity) {
        if (entity.getSentTime() == null)
            return true;
        long duration = System.currentTimeMillis() - entity.getSentTime().getTime();
        return TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS) > config.getExpireTimeSec();
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
