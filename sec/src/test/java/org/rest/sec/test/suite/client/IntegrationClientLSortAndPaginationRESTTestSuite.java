package org.rest.sec.test.suite.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.client.template.test.PrivilegeClientSortAndPaginationRESTIntegrationTest;
import org.rest.sec.client.template.test.RoleClientSortAndPaginationRESTIntegrationTest;
import org.rest.sec.client.template.test.UserClientSortAndPaginationRESTIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
    UserClientSortAndPaginationRESTIntegrationTest.class,
    RoleClientSortAndPaginationRESTIntegrationTest.class,
    PrivilegeClientSortAndPaginationRESTIntegrationTest.class
})// @formatter:on
public final class IntegrationClientLSortAndPaginationRESTTestSuite {
    //
}
