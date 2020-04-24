package com.bluebox.security.authenticationserver.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author kamran ghiasvand
 */

@ConfigurationProperties(value = "app")
@Configuration
@Getter
@Setter
public class AppConfig {

    private PhoneVerificationConfig phoneVerification;
    @Value("${app.production-mode}")
    private Boolean productionMode = false;

}