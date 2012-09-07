package org.rest.sec.web.privilege;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.PrivilegeRESTTemplateImpl;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.PrivilegeEntityOpsImpl;
import org.rest.sec.test.SecSortAndPaginationRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeSortAndPaginationRESTIntegrationTest extends SecSortAndPaginationRESTIntegrationTest<Privilege> {

    @Autowired
    private PrivilegeRESTTemplateImpl template;
    @Autowired
    private PrivilegeEntityOpsImpl entityOps;

    public PrivilegeSortAndPaginationRESTIntegrationTest() {
        super(Privilege.class);
    }

    // tests

    // template method

    @Override
    protected final Privilege createNewEntity() {
        return getEntityOps().createNewEntity();
    }

    @Override
    protected final String getURI() {
        return template.getURI();
    }

    @Override
    protected final PrivilegeRESTTemplateImpl getAPI() {
        return template;
    }

    @Override
    protected IEntityOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
