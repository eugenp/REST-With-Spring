package org.rest.sec.web.user;

import org.rest.client.template.impl.UserRESTTemplateImpl;
import org.rest.sec.dto.User;
import org.rest.sec.test.SecPaginationRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserPaginationRESTIntegrationTest extends SecPaginationRESTIntegrationTest<User> {

    @Autowired
    private UserRESTTemplateImpl template;

    // tests

    // template method (shortcuts)

    @Override
    protected final User createNewEntity() {
	return template.createNewEntity();
    }

    @Override
    protected final String getURI() {
	return template.getURI();
    }

    @Override
    protected final UserRESTTemplateImpl getTemplate() {
	return template;
    }

}
