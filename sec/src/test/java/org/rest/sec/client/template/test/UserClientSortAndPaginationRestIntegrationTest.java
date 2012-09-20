package org.rest.sec.client.template.test;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.UserClientRestTemplate;
import org.rest.sec.model.UserEntityOpsImpl;
import org.rest.sec.model.dto.User;
import org.rest.sec.test.SecClientSortAndPaginationRestIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserClientSortAndPaginationRestIntegrationTest extends SecClientSortAndPaginationRestIntegrationTest<User> {

    @Autowired
    private UserClientRestTemplate clientTemplate;
    @Autowired
    private UserEntityOpsImpl entityOps;

    public UserClientSortAndPaginationRestIntegrationTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final UserClientRestTemplate getAPI() {
        return clientTemplate;
    }

    @Override
    protected final IEntityOperations<User> getEntityOps() {
        return entityOps;
    }

}
