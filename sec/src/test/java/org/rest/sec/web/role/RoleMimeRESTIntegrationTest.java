package org.rest.sec.web.role;

import org.rest.sec.client.template.RoleRESTTemplateImpl;
import org.rest.sec.model.Role;
import org.rest.sec.test.SecMimeRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleMimeRESTIntegrationTest extends SecMimeRESTIntegrationTest<Role> {

    @Autowired
    private RoleRESTTemplateImpl restTemplate;

    public RoleMimeRESTIntegrationTest() {
        super(Role.class);
    }

    // tests

    // template method

    @Override
    protected final RoleRESTTemplateImpl getAPI() {
        return restTemplate;
    }

}
