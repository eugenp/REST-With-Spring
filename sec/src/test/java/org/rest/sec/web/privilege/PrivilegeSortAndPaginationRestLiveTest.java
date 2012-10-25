package org.rest.sec.web.privilege;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.PrivilegeTestRestTemplate;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.PrivilegeEntityOpsImpl;
import org.rest.sec.test.SecSortAndPaginationRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeSortAndPaginationRestLiveTest extends SecSortAndPaginationRestLiveTest<Privilege> {

    @Autowired
    private PrivilegeTestRestTemplate template;
    @Autowired
    private PrivilegeEntityOpsImpl entityOps;

    public PrivilegeSortAndPaginationRestLiveTest() {
        super(Privilege.class);
    }

    // tests

    // template method

    @Override
    protected final Privilege createNewEntity() {
        return getEntityOps().createNewEntity();
    }

    @Override
    protected final String getUri() {
        return template.getUri();
    }

    @Override
    protected final PrivilegeTestRestTemplate getApi() {
        return template;
    }

    @Override
    protected IEntityOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
