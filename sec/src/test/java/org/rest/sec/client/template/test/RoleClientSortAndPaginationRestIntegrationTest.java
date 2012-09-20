package org.rest.sec.client.template.test;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.RoleClientRestTemplate;
import org.rest.sec.model.Role;
import org.rest.sec.model.RoleEntityOpsImpl;
import org.rest.sec.test.SecClientSortAndPaginationRestIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleClientSortAndPaginationRestIntegrationTest extends SecClientSortAndPaginationRestIntegrationTest<Role> {

    @Autowired
    private RoleClientRestTemplate clientTemplate;
    @Autowired
    private RoleEntityOpsImpl entityOps;

    public RoleClientSortAndPaginationRestIntegrationTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final RoleClientRestTemplate getAPI() {
        return clientTemplate;
    }

    @Override
    protected final IEntityOperations<Role> getEntityOps() {
        return entityOps;
    }

}
