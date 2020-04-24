package com.bluebox.security.authenticationserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class AuthenticationServerApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServerApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AuthenticationServerApplication.class, args);
        ConfigurableEnvironment environment = run.getEnvironment();
        String context = environment.getProperty("server.servlet.context-path");
        String base = "http://localhost:" + environment.getProperty("server.port") + context;
        String json = base + environment.getProperty("springfox.documentation.swagger.v2.path");
        String ui = base + "/swagger-ui.html";
        LOGGER.info("resource json address is [" + json + "]");
        LOGGER.info("resource ui address is [" + ui + "]");
    }

}
