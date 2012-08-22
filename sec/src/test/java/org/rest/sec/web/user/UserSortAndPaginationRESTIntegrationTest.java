package org.rest.sec.web.user;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.UserRESTTemplateImpl;
import org.rest.sec.model.UserEntityOpsImpl;
import org.rest.sec.model.dto.User;
import org.rest.sec.test.SecSortAndPaginationRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserSortAndPaginationRESTIntegrationTest extends SecSortAndPaginationRESTIntegrationTest<User> {

    @Autowired
    private UserRESTTemplateImpl template;

    @Autowired
    private UserEntityOpsImpl entityOps;

    public UserSortAndPaginationRESTIntegrationTest() {
        super(User.class);
    }

    // tests

    // template method

    @Override
    protected final User createNewEntity() {
        return template.createNewEntity();
    }

    @Override
    protected final String getURI() {
        return template.getURI();
    }

    @Override
    protected final UserRESTTemplateImpl getAPI() {
        return template;
    }

    @Override
    protected IEntityOperations<User> getEntityOps() {
        return entityOps;
    }

}
