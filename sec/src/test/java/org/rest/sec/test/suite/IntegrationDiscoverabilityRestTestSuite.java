package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.common.web.root.RootDiscoverabilityRestIntegrationTest;
import org.rest.sec.web.privilege.PrivilegeDiscoverabilityRestIntegrationTest;
import org.rest.sec.web.role.RoleDiscoverabilityRestIntegrationTest;
import org.rest.sec.web.user.UserDiscoverabilityRestIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ UserDiscoverabilityRestIntegrationTest.class, RoleDiscoverabilityRestIntegrationTest.class, PrivilegeDiscoverabilityRestIntegrationTest.class, RootDiscoverabilityRestIntegrationTest.class })
public final class IntegrationDiscoverabilityRestTestSuite {
    //
}
