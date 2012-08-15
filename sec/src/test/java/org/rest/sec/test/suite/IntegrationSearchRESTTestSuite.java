package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.web.privilege.PrivilegeSearchRESTIntegrationTest;
import org.rest.sec.web.role.RoleSearchRESTIntegrationTest;
import org.rest.sec.web.user.UserSearchRESTIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({// @formatter:off
    UserSearchRESTIntegrationTest.class,
    RoleSearchRESTIntegrationTest.class,
    PrivilegeSearchRESTIntegrationTest.class
})
// @formatter:off
public final class IntegrationSearchRESTTestSuite {
    //
}
