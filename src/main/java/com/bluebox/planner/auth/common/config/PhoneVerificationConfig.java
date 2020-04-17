package com.bluebox.planner.auth.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author by kamran ghiasvand
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties("app.phone-verification")
public class PhoneVerificationConfig {
    @Value("${app.phone-verification.expire-time-sec}")
    private Integer expireTimeSec;
    @Value("${app.phone-verification.code-len}")
    private Integer codeLen;
}
