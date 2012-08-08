package org.rest.sec.client.template.test;

import org.junit.runner.RunWith;
import org.rest.common.client.AbstractClientRESTIntegrationTest;
import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.RoleRESTTemplateImpl;
import org.rest.sec.client.template.newer.RoleClientRESTTemplate;
import org.rest.sec.model.Role;
import org.rest.sec.spring.client.ClientTestConfig;
import org.rest.sec.spring.context.ContextTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ContextTestConfig.class, ClientTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class RoleClientRESTIntegrationTest extends AbstractClientRESTIntegrationTest<Role> {

    @Autowired
    private RoleClientRESTTemplate clientTemplate;
    @Autowired
    private RoleRESTTemplateImpl entityOps;

    public RoleClientRESTIntegrationTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final RoleClientRESTTemplate getAPI() {
        return clientTemplate;
    }

    @Override
    protected final IEntityOperations<Role> getEntityOps() {
        return entityOps;
    }
}
