package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.web.privilege.PrivilegeSortRESTIntegrationTest;
import org.rest.sec.web.role.RoleSortRESTIntegrationTest;
import org.rest.sec.web.user.UserSortRESTIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ PrivilegeSortRESTIntegrationTest.class, RoleSortRESTIntegrationTest.class, UserSortRESTIntegrationTest.class })
public final class IntegrationSortRESTTestSuite {
    //
}
