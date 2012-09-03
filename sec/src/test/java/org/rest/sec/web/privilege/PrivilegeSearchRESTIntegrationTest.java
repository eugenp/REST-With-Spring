package org.rest.sec.web.privilege;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.PrivilegeRESTTemplateImpl;
import org.rest.sec.model.Privilege;
import org.rest.sec.test.SecSearchRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeSearchRESTIntegrationTest extends SecSearchRESTIntegrationTest<Privilege> {

    @Autowired
    private PrivilegeRESTTemplateImpl restTemplate;

    public PrivilegeSearchRESTIntegrationTest() {
        super();
    }

    // tests

    // template

    @Override
    protected final PrivilegeRESTTemplateImpl getAPI() {
        return restTemplate;
    }

    @Override
    protected final IEntityOperations<Privilege> getEntityOps() {
        return restTemplate;
    }

}
