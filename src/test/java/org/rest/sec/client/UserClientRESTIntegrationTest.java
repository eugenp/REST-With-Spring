package org.rest.sec.client;

import org.junit.runner.RunWith;
import org.rest.client.AbstractClientRESTIntegrationTest;
import org.rest.client.AbstractClientRESTTemplate;
import org.rest.client.template.IEntityOperations;
import org.rest.client.template.impl.UserRESTTemplateImpl;
import org.rest.sec.dto.User;
import org.rest.spring.client.ClientTestConfig;
import org.rest.spring.context.ContextTestConfig;
import org.rest.spring.testing.TestingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ContextTestConfig.class, TestingConfig.class, ClientTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class UserClientRESTIntegrationTest extends AbstractClientRESTIntegrationTest<User> {

    @Autowired
    private UserClientRESTTemplate userClientTemplate;
    @Autowired
    private UserRESTTemplateImpl userEntityOps;

    public UserClientRESTIntegrationTest() {
	super();
    }

    // tests

    // template method

    @Override
    protected final AbstractClientRESTTemplate<User> getTemplate() {
	return userClientTemplate;
    }

    @Override
    protected final IEntityOperations<User> getEntityOps() {
	return userEntityOps;
    }

}
