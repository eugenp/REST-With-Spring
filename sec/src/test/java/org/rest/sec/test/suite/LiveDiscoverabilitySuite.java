package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.common.web.root.RootDiscoverabilityRestLiveTest;
import org.rest.sec.web.privilege.PrivilegeDiscoverabilityRestLiveTest;
import org.rest.sec.web.role.RoleDiscoverabilityRestLiveTest;
import org.rest.sec.web.user.UserDiscoverabilityRestLiveTest;

@RunWith(Suite.class)
@SuiteClasses({ UserDiscoverabilityRestLiveTest.class, RoleDiscoverabilityRestLiveTest.class, PrivilegeDiscoverabilityRestLiveTest.class, RootDiscoverabilityRestLiveTest.class })
public final class LiveDiscoverabilitySuite {
    //
}
