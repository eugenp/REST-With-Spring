package org.rest.sec.client.template.test;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.PrivilegeClientRESTTemplate;
import org.rest.sec.client.template.PrivilegeRESTTemplateImpl;
import org.rest.sec.model.Privilege;
import org.rest.sec.test.SecClientSortAndPaginationRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeClientSortAndPaginationRESTIntegrationTest extends SecClientSortAndPaginationRESTIntegrationTest<Privilege> {

    @Autowired
    private PrivilegeClientRESTTemplate clientTemplate;
    @Autowired
    private PrivilegeRESTTemplateImpl entityOps;

    public PrivilegeClientSortAndPaginationRESTIntegrationTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final PrivilegeClientRESTTemplate getAPI() {
        return clientTemplate;
    }

    @Override
    protected final IEntityOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
