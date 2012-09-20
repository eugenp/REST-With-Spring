package org.rest.sec.web.user;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.UserTestRestTemplate;
import org.rest.sec.model.UserEntityOpsImpl;
import org.rest.sec.model.dto.User;
import org.rest.sec.test.SecSortAndPaginationRestIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserSortAndPaginationRestIntegrationTest extends SecSortAndPaginationRestIntegrationTest<User> {

    @Autowired
    private UserTestRestTemplate template;

    @Autowired
    private UserEntityOpsImpl entityOps;

    public UserSortAndPaginationRestIntegrationTest() {
        super(User.class);
    }

    // tests

    // template method

    @Override
    protected final User createNewEntity() {
        return getEntityOps().createNewEntity();
    }

    @Override
    protected final String getURI() {
        return template.getURI();
    }

    @Override
    protected final UserTestRestTemplate getAPI() {
        return template;
    }

    @Override
    protected IEntityOperations<User> getEntityOps() {
        return entityOps;
    }

}
