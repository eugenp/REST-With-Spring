package org.rest.sec.web.user;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.UserTestRestTemplate;
import org.rest.sec.model.UserEntityOpsImpl;
import org.rest.sec.model.dto.User;
import org.rest.sec.test.SecSortAndPaginationRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserSortAndPaginationRestLiveTest extends SecSortAndPaginationRestLiveTest<User> {

    @Autowired
    private UserTestRestTemplate template;

    @Autowired
    private UserEntityOpsImpl entityOps;

    public UserSortAndPaginationRestLiveTest() {
        super(User.class);
    }

    // tests

    // template method

    @Override
    protected final User createNewEntity() {
        return getEntityOps().createNewEntity();
    }

    @Override
    protected final String getUri() {
        return template.getUri();
    }

    @Override
    protected final UserTestRestTemplate getApi() {
        return template;
    }

    @Override
    protected IEntityOperations<User> getEntityOps() {
        return entityOps;
    }

}
