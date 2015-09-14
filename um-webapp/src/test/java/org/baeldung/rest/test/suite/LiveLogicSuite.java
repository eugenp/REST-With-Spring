package org.baeldung.rest.test.suite;

import org.baeldung.rest.security.AuthenticationRestLiveTest;
import org.baeldung.rest.web.privilege.PrivilegeLogicRestLiveTest;
import org.baeldung.rest.web.role.RoleLogicRestLiveTest;
import org.baeldung.rest.web.user.UserLogicRestLiveTest;
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
