package org.baeldung.rest.web;

import org.baeldung.rest.spring.ContextConfig;
import org.baeldung.rest.spring.PersistenceJpaConfig;
import org.baeldung.rest.spring.SecCommonApiConfig;
import org.baeldung.rest.spring.ServiceConfig;
import org.baeldung.rest.spring.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceJpaConfig.class, ContextConfig.class, ServiceConfig.class, SecCommonApiConfig.class, WebConfig.class })
@WebAppConfiguration
public class WebSpringIntegrationTest {

    @Test
    public final void whenContextIsBootstrapped_thenOk() {
        //
    }

}
