package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.common.search.ConstructQueryStringUnitTest;
import org.rest.common.util.ParseQueryStringUnitTest;
import org.rest.sec.persistence.service.PrincipalSearchPersistenceIntegrationTest;
import org.rest.sec.persistence.service.PrivilegeSearchPersistenceIntegrationTest;
import org.rest.sec.persistence.service.RoleSearchPersistenceIntegrationTest;
import org.rest.sec.web.privilege.PrivilegeSearchRESTIntegrationTest;
import org.rest.sec.web.role.RoleSearchRESTIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({// @formatter:off
ParseQueryStringUnitTest.class, ConstructQueryStringUnitTest.class,

PrincipalSearchPersistenceIntegrationTest.class, RoleSearchPersistenceIntegrationTest.class, PrivilegeSearchPersistenceIntegrationTest.class,

RoleSearchRESTIntegrationTest.class, PrivilegeSearchRESTIntegrationTest.class })
// @formatter:on
public final class SearchTestSuite {
    //
}
