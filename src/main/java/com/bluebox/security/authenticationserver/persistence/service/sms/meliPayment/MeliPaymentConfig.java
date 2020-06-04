package com.bluebox.security.authenticationserver.persistence.service.sms.meliPayment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author kamran ghiasvand
 */

@ConfigurationProperties(value = "app.sms.provider")
@Configuration
@Getter
@Setter
public class MeliPaymentConfig {

    private String url;
    private String username;
    private String password;
    private String from;

}