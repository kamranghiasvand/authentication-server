package com.bluebox.security.authenticationserver.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author kamran ghiasvand
 */

@ConfigurationProperties("input")
@Configuration
public class InputProperties {
    @Getter
    @Setter
    private int textMaxLength = 200;
    @Getter
    @Setter
    private int longTextMaxLength = 1000;

}