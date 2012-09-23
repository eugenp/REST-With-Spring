package org.rest.sec.test.suite.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.client.template.test.PrivilegeSortAndPaginationClientRestIntegrationTest;
import org.rest.sec.client.template.test.RoleSortAndPaginationClientRestIntegrationTest;
import org.rest.sec.client.template.test.UserSortAndPaginationClientRestIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
    UserSortAndPaginationClientRestIntegrationTest.class,
    RoleSortAndPaginationClientRestIntegrationTest.class,
    PrivilegeSortAndPaginationClientRestIntegrationTest.class
})// @formatter:on
public final class IntegrationClientLSortAndPaginationRestTestSuite {
    //
}
