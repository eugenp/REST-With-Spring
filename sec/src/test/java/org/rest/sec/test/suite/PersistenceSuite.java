package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.persistence.service.PrincipalSearchPersistenceIntegrationTest;
import org.rest.sec.persistence.service.PrincipalServicePersistenceIntegrationTest;
import org.rest.sec.persistence.service.PrivilegeSearchPersistenceIntegrationTest;
import org.rest.sec.persistence.service.PrivilegeServicePersistenceIntegrationTest;
import org.rest.sec.persistence.service.RoleSearchPersistenceIntegrationTest;
import org.rest.sec.persistence.service.RoleServicePersistenceIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({// @formatter:off
    PrincipalSearchPersistenceIntegrationTest.class, PrincipalServicePersistenceIntegrationTest.class,
    PrivilegeSearchPersistenceIntegrationTest.class, PrivilegeServicePersistenceIntegrationTest.class,
    RoleSearchPersistenceIntegrationTest.class, RoleServicePersistenceIntegrationTest.class
})
// @formatter:on
public final class PersistenceSuite {
    //
}
