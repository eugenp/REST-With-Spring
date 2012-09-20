package org.rest.sec.test.suite.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.client.template.test.PrivilegeClientSortAndPaginationRestIntegrationTest;
import org.rest.sec.client.template.test.RoleClientSortAndPaginationRestIntegrationTest;
import org.rest.sec.client.template.test.UserClientSortAndPaginationRestIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
    UserClientSortAndPaginationRestIntegrationTest.class,
    RoleClientSortAndPaginationRestIntegrationTest.class,
    PrivilegeClientSortAndPaginationRestIntegrationTest.class
})// @formatter:on
public final class IntegrationClientLSortAndPaginationRestTestSuite {
    //
}
