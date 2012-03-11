package org.rest.sec.web.user;

import org.rest.client.template.impl.UserRESTTemplateImpl;
import org.rest.sec.dto.User;
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
    protected final UserRESTTemplateImpl getTemplate() {
	return restTemplate;
    }

}
