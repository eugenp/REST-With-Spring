package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.web.privilege.PrivilegeSearchRestLiveTest;
import org.rest.sec.web.role.RoleSearchRestLiveTest;
import org.rest.sec.web.user.UserSearchRestLiveTest;

@RunWith(Suite.class)
@SuiteClasses({// @formatter:off
    UserSearchRestLiveTest.class,
    RoleSearchRestLiveTest.class,
    PrivilegeSearchRestLiveTest.class
})
// @formatter:off
public final class LiveSearchSuite {
    //
}
