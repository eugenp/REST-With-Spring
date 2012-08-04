package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.web.privilege.PrivilegeDiscoverabilityRESTIntegrationTest;
import org.rest.sec.web.role.RoleDiscoverabilityRESTIntegrationTest;
import org.rest.sec.web.user.UserDiscoverabilityRESTIntegrationTest;
import org.rest.web.root.RootDiscoverabilityRESTIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ UserDiscoverabilityRESTIntegrationTest.class, RoleDiscoverabilityRESTIntegrationTest.class, PrivilegeDiscoverabilityRESTIntegrationTest.class, RootDiscoverabilityRESTIntegrationTest.class })
public final class IntegrationDiscoverabilityRESTTestSuite {
    //
}
