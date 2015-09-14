package org.baeldung.um.test.suite;

import org.baeldung.um.security.AuthenticationRestLiveTest;
import org.baeldung.um.web.privilege.PrivilegeLogicRestLiveTest;
import org.baeldung.um.web.role.RoleLogicRestLiveTest;
import org.baeldung.um.web.user.UserLogicRestLiveTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ // @formatter:off
    UserLogicRestLiveTest.class,
    RoleLogicRestLiveTest.class,
    PrivilegeLogicRestLiveTest.class,

    AuthenticationRestLiveTest.class
})
// @formatter:off
public final class LiveLogicSuite {
    //
}
