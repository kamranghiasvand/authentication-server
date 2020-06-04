package com.bluebox.security.authenticationserver.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }
}