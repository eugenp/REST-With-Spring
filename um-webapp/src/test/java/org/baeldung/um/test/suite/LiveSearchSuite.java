package org.baeldung.um.test.suite;

import org.baeldung.um.web.privilege.PrivilegeSearchRestLiveTest;
import org.baeldung.um.web.role.RoleSearchRestLiveTest;
import org.baeldung.um.web.user.UserSearchRestLiveTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ // @formatter:off
    UserSearchRestLiveTest.class,
    RoleSearchRestLiveTest.class,
    PrivilegeSearchRestLiveTest.class
})
// @formatter:off
public final class LiveSearchSuite {
    //
}
