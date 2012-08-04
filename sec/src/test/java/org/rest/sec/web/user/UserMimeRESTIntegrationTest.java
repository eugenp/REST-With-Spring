package org.rest.sec.web.user;

import org.rest.sec.client.template.UserRESTTemplateImpl;
import org.rest.sec.model.dto.User;
import org.rest.sec.test.SecMimeRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMimeRESTIntegrationTest extends SecMimeRESTIntegrationTest<User> {

    @Autowired
    private UserRESTTemplateImpl restTemplate;

    public UserMimeRESTIntegrationTest() {
        super(User.class);
    }

    // tests

    // template method

    @Override
    protected final UserRESTTemplateImpl getAPI() {
        return restTemplate;
    }

}
