package org.baeldung.rest.test.suite;

import org.baeldung.rest.service.main.PrincipalServiceIntegrationTest;
import org.baeldung.rest.service.main.PrivilegeServiceIntegrationTest;
import org.baeldung.rest.service.main.RoleServiceIntegrationTest;
import org.baeldung.rest.service.search.PrincipalSearchIntegrationTest;
import org.baeldung.rest.service.search.PrivilegeSearchIntegrationTest;
import org.baeldung.rest.service.search.RoleSearchIntegrationTest;
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
