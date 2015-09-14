package org.baeldung.rest.test.suite;

import org.baeldung.rest.common.web.root.RootDiscoverabilityRestLiveTest;
import org.baeldung.rest.web.privilege.PrivilegeDiscoverabilityRestLiveTest;
import org.baeldung.rest.web.role.RoleDiscoverabilityRestLiveTest;
import org.baeldung.rest.web.user.UserDiscoverabilityRestLiveTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UserDiscoverabilityRestLiveTest.class, RoleDiscoverabilityRestLiveTest.class, PrivilegeDiscoverabilityRestLiveTest.class, RootDiscoverabilityRestLiveTest.class })
public final class LiveDiscoverabilitySuite {
    //
}
