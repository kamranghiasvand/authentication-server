package com.bluebox.planner.auth.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

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