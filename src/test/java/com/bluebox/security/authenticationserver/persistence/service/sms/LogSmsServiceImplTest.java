package com.bluebox.security.authenticationserver.persistence.service.sms;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * @author by kamran ghiasvand
 */
@SpringBootTest(properties = {"app.production-mode=false"})
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LogSmsServiceImplTest {
    @Mock
    private Logger logger;
    private List<String> logs = new ArrayList<>();

    @BeforeEach
    public void beforeEach() {
        doAnswer(args -> {
            String mess = args.getArgument(0);
            Object arg1 = args.getArgument(1);
            Object arg2 = args.getArgument(2);

            logs.add(String.format(mess, arg1, arg2));
            return null;
        }).when(logger).info(anyString(), any(), any());

        when(LoggerFactory.getLogger(Mockito.any(Class.class))).thenReturn(logger);

    }

    @Test
    @Order(1)
    public void mockingIsOk() {
    }

}
