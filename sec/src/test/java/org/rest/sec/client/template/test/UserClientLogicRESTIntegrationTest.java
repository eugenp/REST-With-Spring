package org.rest.sec.client.template.test;

import org.junit.runner.RunWith;
import org.rest.common.client.AbstractClientLogicRESTIntegrationTest;
import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.UserClientRESTTemplate;
import org.rest.sec.client.template.UserRESTTemplateImpl;
import org.rest.sec.model.dto.User;
import org.rest.sec.spring.client.ClientTestConfig;
import org.rest.sec.spring.context.ContextTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ContextTestConfig.class, ClientTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class UserClientLogicRESTIntegrationTest extends AbstractClientLogicRESTIntegrationTest<User> {

    @Autowired
    private UserClientRESTTemplate clientTemplate;
    @Autowired
    private UserRESTTemplateImpl entityOps;

    public UserClientLogicRESTIntegrationTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final UserClientRESTTemplate getAPI() {
        return clientTemplate;
    }

    @Override
    protected final IEntityOperations<User> getEntityOps() {
        return entityOps;
    }

}
