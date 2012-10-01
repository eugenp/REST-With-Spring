package org.rest.sec.client.template.test;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.RoleClientRestTemplate;
import org.rest.sec.model.Role;
import org.rest.sec.model.RoleEntityOpsImpl;
import org.rest.sec.test.SecSortAndPaginationClientRestIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleSortAndPaginationClientRestIntegrationTest extends SecSortAndPaginationClientRestIntegrationTest<Role> {

    @Autowired
    private RoleClientRestTemplate api;
    @Autowired
    private RoleEntityOpsImpl entityOps;

    public RoleSortAndPaginationClientRestIntegrationTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final RoleClientRestTemplate getAPI() {
        return api;
    }

    @Override
    protected final IEntityOperations<Role> getEntityOps() {
        return entityOps;
    }

}
