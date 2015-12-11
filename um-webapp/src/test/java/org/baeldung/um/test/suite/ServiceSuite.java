package org.baeldung.um.test.suite;

import org.baeldung.um.persistence.PersistenceSpringIntegrationTest;
import org.baeldung.um.service.ServiceSpringIntegrationTest;
import org.baeldung.um.service.main.PrincipalServiceIntegrationTest;
import org.baeldung.um.service.main.PrivilegeServiceIntegrationTest;
import org.baeldung.um.service.main.RoleServiceIntegrationTest;
import org.baeldung.um.service.search.PrincipalSearchIntegrationTest;
import org.baeldung.um.service.search.PrivilegeSearchIntegrationTest;
import org.baeldung.um.service.search.RoleSearchIntegrationTest;
import org.baeldung.um.web.WebSpringIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ // @formatter:off
    PrincipalSearchIntegrationTest.class
    ,PrincipalServiceIntegrationTest.class

    ,PrivilegeSearchIntegrationTest.class
    ,PrivilegeServiceIntegrationTest.class

    ,RoleSearchIntegrationTest.class
    ,RoleServiceIntegrationTest.class

    ,WebSpringIntegrationTest.class
    ,ServiceSpringIntegrationTest.class
    ,PersistenceSpringIntegrationTest.class
})
// @formatter:on
public final class ServiceSuite {
    //
}
