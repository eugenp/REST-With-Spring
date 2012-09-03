package org.rest.sec.web.user;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.UserRESTTemplateImpl;
import org.rest.sec.model.dto.User;
import org.rest.sec.test.SecSearchRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserSearchRESTIntegrationTest extends SecSearchRESTIntegrationTest<User> {

    @Autowired
    private UserRESTTemplateImpl restTemplate;

    public UserSearchRESTIntegrationTest() {
        super();
    }

    // tests

    // template

    @Override
    protected final UserRESTTemplateImpl getAPI() {
        return restTemplate;
    }

    @Override
    protected final IEntityOperations<User> getEntityOps() {
        return restTemplate;
    }

}
