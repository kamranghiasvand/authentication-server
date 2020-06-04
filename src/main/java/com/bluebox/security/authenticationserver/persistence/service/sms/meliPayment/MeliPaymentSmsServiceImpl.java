package com.bluebox.security.authenticationserver.persistence.service.sms.meliPayment;


import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.common.exception.ValidationException;
import com.bluebox.security.authenticationserver.persistence.service.sms.SmsProviderException;
import com.bluebox.security.authenticationserver.persistence.service.sms.SmsService;
import com.bluebox.security.authenticationserver.persistence.service.sms.meliPayment.dto.MeliPaymentReq;
import com.bluebox.security.authenticationserver.persistence.service.sms.meliPayment.dto.MeliPaymentResp;
import com.bluebox.security.authenticationserver.validators.RuleFactory;
import com.bluebox.security.authenticationserver.validators.ValidationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @author by kamran ghiasvand
 */
@Service
@ConditionalOnProperty(name = "app.production-mode", havingValue = "true")
@Primary
public class MeliPaymentSmsServiceImpl implements SmsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MeliPaymentSmsServiceImpl.class);
    private final MeliPaymentConfig config;
    private final RestTemplate restTemplate;

    @Autowired
    public MeliPaymentSmsServiceImpl(MeliPaymentConfig config, RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendText(String phone, String text) throws ValidationException, SmsProviderException {
        validateInputs(phone, text);
        LOGGER.info("sending text '{}' to '{}'", text, phone);
        final var request = MeliPaymentReq.newBuilder()
                .form(config.getFrom())
                .username(config.getUsername())
                .password(config.getPassword())
                .text(text)
                .to(phone)
                .isFlash(false)
                .build();
        ResponseEntity<MeliPaymentResp> response;
        try {
            response = restTemplate.postForEntity(config.getUrl(), request, MeliPaymentResp.class);
        } catch (RestClientException e) {
            throw new SmsProviderException(e.getMessage());
        }

        if (response.getStatusCode() != HttpStatus.OK) {
            final var body = response.getBody();
            assert body != null;
            throw new SmsProviderException(body.getRetStatus() + " : " + body.getStrRetStatus());
        }
    }

    private void validateInputs(String phone, String text) throws ValidationException {
        final var PHONE_FIELD_NAME = "phone";
        final var TEXT_FIELD_NAME = "text";

        ValidationContext<String> phoneCtx = new ValidationContext<>();
        phoneCtx.add(RuleFactory.nullRule(s -> phone, PHONE_FIELD_NAME));
        phoneCtx.add(RuleFactory.validPhone(o -> phone, PHONE_FIELD_NAME));
        phoneCtx.validate(phone);

        ValidationContext<String> textCtx = new ValidationContext<>();
        final var textLenRule = RuleFactory.nullEmptyLenRule((String o) -> text, TEXT_FIELD_NAME, 100);
        textCtx.add(textLenRule.getNullRule());
        textCtx.add(textLenRule.getEmptyRule());
        textCtx.validate(text);
    }
}
