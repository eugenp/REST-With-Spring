package org.rest.sec.client.template.test;

import org.junit.runner.RunWith;
import org.rest.common.client.AbstractClientRESTIntegrationTest;
import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.UserRESTTemplateImpl;
import org.rest.sec.client.template.newer.UserClientRESTTemplate;
import org.rest.sec.model.dto.User;
import org.rest.spring.client.ClientTestConfig;
import org.rest.spring.context.ContextTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ContextTestConfig.class, ClientTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class UserClientRESTIntegrationTest extends AbstractClientRESTIntegrationTest<User> {

    @Autowired private UserClientRESTTemplate userClientTemplate;
    @Autowired private UserRESTTemplateImpl userEntityOps;

    public UserClientRESTIntegrationTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final UserClientRESTTemplate getAPI() {
        return userClientTemplate;
    }

    @Override
    protected final IEntityOperations<User> getEntityOps() {
        return userEntityOps;
    }

}
