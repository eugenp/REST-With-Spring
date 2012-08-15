package org.rest.sec.web.role;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.RoleRESTTemplateImpl;
import org.rest.sec.model.Role;
import org.rest.sec.model.RoleEntityOpsImpl;
import org.rest.sec.test.SecMimeRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleMimeRESTIntegrationTest extends SecMimeRESTIntegrationTest<Role> {

    @Autowired
    private RoleRESTTemplateImpl api;
    @Autowired
    private RoleEntityOpsImpl entityOps;

    public RoleMimeRESTIntegrationTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final RoleRESTTemplateImpl getAPI() {
        return api;
    }

    @Override
    protected final IEntityOperations<Role> getEntityOps() {
        return entityOps;
    }

}
