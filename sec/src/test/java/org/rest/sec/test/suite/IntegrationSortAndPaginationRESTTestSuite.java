package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.web.privilege.PrivilegeSortAndPaginationRESTIntegrationTest;
import org.rest.sec.web.role.RoleSortAndPaginationRESTIntegrationTest;
import org.rest.sec.web.user.UserSortAndPaginationRESTIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ PrivilegeSortAndPaginationRESTIntegrationTest.class, RoleSortAndPaginationRESTIntegrationTest.class, UserSortAndPaginationRESTIntegrationTest.class })
public final class IntegrationSortAndPaginationRESTTestSuite {
    //
}
