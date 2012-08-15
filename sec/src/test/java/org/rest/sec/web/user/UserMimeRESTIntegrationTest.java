package org.rest.sec.web.user;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.UserRESTTemplateImpl;
import org.rest.sec.model.UserEntityOpsImpl;
import org.rest.sec.model.dto.User;
import org.rest.sec.test.SecMimeRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMimeRESTIntegrationTest extends SecMimeRESTIntegrationTest<User> {

    @Autowired
    private UserRESTTemplateImpl api;
    @Autowired
    private UserEntityOpsImpl entityOps;

    public UserMimeRESTIntegrationTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final UserRESTTemplateImpl getAPI() {
        return api;
    }

    @Override
    protected final IEntityOperations<User> getEntityOps() {
        return entityOps;
    }

}
