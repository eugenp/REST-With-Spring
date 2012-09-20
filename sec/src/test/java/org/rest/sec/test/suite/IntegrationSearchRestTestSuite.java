package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.web.privilege.PrivilegeSearchRestIntegrationTest;
import org.rest.sec.web.role.RoleSearchRestIntegrationTest;
import org.rest.sec.web.user.UserSearchRestIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({// @formatter:off
    UserSearchRestIntegrationTest.class,
    RoleSearchRestIntegrationTest.class,
    PrivilegeSearchRestIntegrationTest.class
})
// @formatter:off
public final class IntegrationSearchRestTestSuite {
    //
}
