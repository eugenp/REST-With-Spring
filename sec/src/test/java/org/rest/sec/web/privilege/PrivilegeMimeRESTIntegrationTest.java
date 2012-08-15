package org.rest.sec.web.privilege;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.PrivilegeRESTTemplateImpl;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.PrivilegeEntityOpsImpl;
import org.rest.sec.test.SecMimeRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeMimeRESTIntegrationTest extends SecMimeRESTIntegrationTest<Privilege> {

    @Autowired
    private PrivilegeRESTTemplateImpl api;
    @Autowired
    private PrivilegeEntityOpsImpl entityOps;

    public PrivilegeMimeRESTIntegrationTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final PrivilegeRESTTemplateImpl getAPI() {
        return api;
    }

    @Override
    protected final IEntityOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
