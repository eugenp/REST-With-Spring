package org.rest.sec.web.role;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.RoleRESTTemplateImpl;
import org.rest.sec.model.Role;
import org.rest.sec.test.SecSearchRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleSearchRESTIntegrationTest extends SecSearchRESTIntegrationTest<Role> {

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
    protected final IEntityOperations<Role> getEntityOps() {
        return restTemplate;
    }

}
