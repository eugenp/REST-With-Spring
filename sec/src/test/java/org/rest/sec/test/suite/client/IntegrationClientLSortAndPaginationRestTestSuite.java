package org.rest.sec.test.suite.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.client.template.test.PrivilegeSortAndPaginationClientRestLiveTest;
import org.rest.sec.client.template.test.RoleSortAndPaginationClientRestLiveTest;
import org.rest.sec.client.template.test.UserSortAndPaginationClientRestLiveTest;

@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
    UserSortAndPaginationClientRestLiveTest.class,
    RoleSortAndPaginationClientRestLiveTest.class,
    PrivilegeSortAndPaginationClientRestLiveTest.class
})// @formatter:on
public final class IntegrationClientLSortAndPaginationRestTestSuite {
    //
}
