package org.rest.sec.web.role;

import org.junit.runner.RunWith;
import org.rest.common.client.IEntityOperations;
import org.rest.common.web.base.AbstractSearchRESTIntegrationTest;
import org.rest.sec.client.template.RoleRESTTemplateImpl;
import org.rest.sec.model.Role;
import org.rest.sec.spring.client.ClientTestConfig;
import org.rest.sec.spring.context.ContextTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientTestConfig.class, ContextTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class RoleSearchRESTIntegrationTest extends AbstractSearchRESTIntegrationTest<Role> {

    @Autowired
    private RoleRESTTemplateImpl restTemplate;

    public RoleSearchRESTIntegrationTest() {
        super();
    }

    // tests

    // template

    @Override
    protected final RoleRESTTemplateImpl getAPI() {
        return restTemplate;
    }

    @Override
    protected final IEntityOperations<Role> getEntityOperations() {
        return restTemplate;
    }

}
