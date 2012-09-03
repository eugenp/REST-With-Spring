package org.rest.sec.client.template.test;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.RoleClientRESTTemplate;
import org.rest.sec.client.template.RoleRESTTemplateImpl;
import org.rest.sec.model.Role;
import org.rest.sec.test.SecClientLogicRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleClientLogicRESTIntegrationTest extends SecClientLogicRESTIntegrationTest<Role> {

    @Autowired
    private RoleClientRESTTemplate clientTemplate;
    @Autowired
    private RoleRESTTemplateImpl entityOps;

    public RoleClientLogicRESTIntegrationTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final RoleClientRESTTemplate getAPI() {
        return clientTemplate;
    }

    @Override
    protected final IEntityOperations<Role> getEntityOps() {
        return entityOps;
    }
}
