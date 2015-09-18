package org.baeldung.um.test.suite;

import org.baeldung.um.service.main.PrincipalServiceIntegrationTest;
import org.baeldung.um.service.main.PrivilegeServiceIntegrationTest;
import org.baeldung.um.service.main.RoleServiceIntegrationTest;
import org.baeldung.um.service.search.PrincipalSearchIntegrationTest;
import org.baeldung.um.service.search.PrivilegeSearchIntegrationTest;
import org.baeldung.um.service.search.RoleSearchIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ // @formatter:off
    PrincipalSearchIntegrationTest.class, PrincipalServiceIntegrationTest.class,
    PrivilegeSearchIntegrationTest.class, PrivilegeServiceIntegrationTest.class,
    RoleSearchIntegrationTest.class, RoleServiceIntegrationTest.class
})
// @formatter:on
public final class ServiceSuite {
    //
}
