package org.rest.sec.client.template.test;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.UserClientRESTTemplate;
import org.rest.sec.model.UserEntityOpsImpl;
import org.rest.sec.model.dto.User;
import org.rest.sec.test.SecClientSortAndPaginationRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserClientSortAndPaginationRESTIntegrationTest extends SecClientSortAndPaginationRESTIntegrationTest<User> {

    @Autowired
    private UserClientRESTTemplate clientTemplate;
    @Autowired
    private UserEntityOpsImpl entityOps;

    public UserClientSortAndPaginationRESTIntegrationTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final UserClientRESTTemplate getAPI() {
        return clientTemplate;
    }

    @Override
    protected final IEntityOperations<User> getEntityOps() {
        return entityOps;
    }

}
