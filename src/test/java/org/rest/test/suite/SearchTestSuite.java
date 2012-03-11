package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.persistence.service.RoleServiceSearchPersistenceIntegrationTest;
import org.rest.sec.util.ConstructQueryStringUnitTest;
import org.rest.sec.util.ParseQueryStringUnitTest;
import org.rest.sec.web.role.RoleSearchRESTIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ ParseQueryStringUnitTest.class, ConstructQueryStringUnitTest.class, RoleServiceSearchPersistenceIntegrationTest.class, RoleSearchRESTIntegrationTest.class })
public final class SearchTestSuite {
    //
}
