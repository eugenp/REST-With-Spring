package org.baeldung.um.test.suite;

import org.baeldung.um.common.web.root.RootDiscoverabilityRestLiveTest;
import org.baeldung.um.web.privilege.PrivilegeDiscoverabilityRestLiveTest;
import org.baeldung.um.web.role.RoleDiscoverabilityRestLiveTest;
import org.baeldung.um.web.user.UserDiscoverabilityRestLiveTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UserDiscoverabilityRestLiveTest.class, RoleDiscoverabilityRestLiveTest.class, PrivilegeDiscoverabilityRestLiveTest.class, RootDiscoverabilityRestLiveTest.class })
public final class LiveDiscoverabilitySuite {
    //
}
