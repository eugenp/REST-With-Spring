package org.rest.sec.web.privilege;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.PrivilegeRESTTemplateImpl;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.PrivilegeEntityOpsImpl;
import org.rest.sec.test.SecLogicRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeLogicRESTIntegrationTest extends SecLogicRESTIntegrationTest<Privilege> {

    @Autowired
    private PrivilegeRESTTemplateImpl api;
    @Autowired
    private PrivilegeEntityOpsImpl entityOps;

    public PrivilegeLogicRESTIntegrationTest() {
        super(Privilege.class);
    }

    // tests

    // template

    @Override
    protected final PrivilegeRESTTemplateImpl getAPI() {
        return api;
    }

    @Override
    protected final IEntityOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
