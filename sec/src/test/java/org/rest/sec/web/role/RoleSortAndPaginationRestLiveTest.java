package org.rest.sec.web.role;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.RoleTestRestTemplate;
import org.rest.sec.model.Role;
import org.rest.sec.model.RoleEntityOpsImpl;
import org.rest.sec.test.SecSortAndPaginationRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleSortAndPaginationRestLiveTest extends SecSortAndPaginationRestLiveTest<Role> {

    @Autowired
    private RoleTestRestTemplate template;

    @Autowired
    private RoleEntityOpsImpl entityOps;

    public RoleSortAndPaginationRestLiveTest() {
        super(Role.class);
    }

    // tests

    // template method

    @Override
    protected final Role createNewEntity() {
        return getEntityOps().createNewEntity();
    }

    @Override
    protected final String getUri() {
        return template.getUri();
    }

    @Override
    protected final RoleTestRestTemplate getApi() {
        return template;
    }

    @Override
    protected IEntityOperations<Role> getEntityOps() {
        return entityOps;
    }

}
