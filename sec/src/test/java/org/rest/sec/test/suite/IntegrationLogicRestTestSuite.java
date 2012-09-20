package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.security.AuthenticationRestIntegrationTest;
import org.rest.sec.web.privilege.PrivilegeLogicRestIntegrationTest;
import org.rest.sec.web.role.RoleLogicRestIntegrationTest;
import org.rest.sec.web.user.UserLogicRestIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({// @formatter:off
    UserLogicRestIntegrationTest.class,
    RoleLogicRestIntegrationTest.class,
    PrivilegeLogicRestIntegrationTest.class,

    AuthenticationRestIntegrationTest.class
})
// @formatter:off
public final class IntegrationLogicRestTestSuite {
    //
}
