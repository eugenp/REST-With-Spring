package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.web.privilege.PrivilegeSortAndPaginationRestLiveTest;
import org.rest.sec.web.role.RoleSortAndPaginationRestLiveTest;
import org.rest.sec.web.user.UserSortAndPaginationRestLiveTest;

@RunWith(Suite.class)
@SuiteClasses({ PrivilegeSortAndPaginationRestLiveTest.class, RoleSortAndPaginationRestLiveTest.class, UserSortAndPaginationRestLiveTest.class })
public final class IntegrationSortAndPaginationRestTestSuite {
    //
}
