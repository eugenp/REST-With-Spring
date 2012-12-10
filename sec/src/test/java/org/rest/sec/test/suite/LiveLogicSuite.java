package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.security.AuthenticationRestLiveTest;
import org.rest.sec.web.privilege.PrivilegeLogicRestLiveTest;
import org.rest.sec.web.role.RoleLogicRestLiveTest;
import org.rest.sec.web.user.UserLogicRestLiveTest;

@RunWith(Suite.class)
@SuiteClasses({// @formatter:off
    UserLogicRestLiveTest.class,
    RoleLogicRestLiveTest.class,
    PrivilegeLogicRestLiveTest.class,

    AuthenticationRestLiveTest.class
})
// @formatter:off
public final class LiveLogicSuite {
    //
}
