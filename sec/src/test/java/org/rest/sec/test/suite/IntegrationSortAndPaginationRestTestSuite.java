package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.web.privilege.PrivilegeSortAndPaginationRestIntegrationTest;
import org.rest.sec.web.role.RoleSortAndPaginationRestIntegrationTest;
import org.rest.sec.web.user.UserSortAndPaginationRestIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ PrivilegeSortAndPaginationRestIntegrationTest.class, RoleSortAndPaginationRestIntegrationTest.class, UserSortAndPaginationRestIntegrationTest.class })
public final class IntegrationSortAndPaginationRestTestSuite {
    //
}
