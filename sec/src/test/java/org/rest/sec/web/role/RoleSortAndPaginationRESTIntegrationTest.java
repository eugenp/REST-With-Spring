package org.rest.sec.web.role;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.RoleRESTTemplateImpl;
import org.rest.sec.model.Role;
import org.rest.sec.model.RoleEntityOpsImpl;
import org.rest.sec.test.SecSortAndPaginationRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleSortAndPaginationRESTIntegrationTest extends SecSortAndPaginationRESTIntegrationTest<Role> {

    @Autowired
    private RoleRESTTemplateImpl template;

    @Autowired
    private RoleEntityOpsImpl entityOps;

    public RoleSortAndPaginationRESTIntegrationTest() {
        super(Role.class);
    }

    // tests

    // template method

    @Override
    protected final Role createNewEntity() {
        return getEntityOps().createNewEntity();
    }

    @Override
    protected final String getURI() {
        return template.getURI();
    }

    @Override
    protected final RoleRESTTemplateImpl getAPI() {
        return template;
    }

    @Override
    protected IEntityOperations<Role> getEntityOps() {
        return entityOps;
    }

}
