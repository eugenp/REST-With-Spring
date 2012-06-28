package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.persistence.service.PrincipalSearchPersistenceIntegrationTest;
import org.rest.sec.persistence.service.PrivilegeSearchPersistenceIntegrationTest;
import org.rest.sec.persistence.service.RoleSearchPersistenceIntegrationTest;
import org.rest.sec.util.ConstructQueryStringUnitTest;
import org.rest.sec.web.privilege.PrivilegeSearchRESTIntegrationTest;
import org.rest.sec.web.role.RoleSearchRESTIntegrationTest;
import org.rest.util.ParseQueryStringUnitTest;

@RunWith( Suite.class )
@SuiteClasses( {// @formatter:off
    ParseQueryStringUnitTest.class,
    ConstructQueryStringUnitTest.class,

    PrincipalSearchPersistenceIntegrationTest.class,
    RoleSearchPersistenceIntegrationTest.class,
    PrivilegeSearchPersistenceIntegrationTest.class,

    RoleSearchRESTIntegrationTest.class,
    PrivilegeSearchRESTIntegrationTest.class
}) // @formatter:on
public final class SearchTestSuite{
	//
}
