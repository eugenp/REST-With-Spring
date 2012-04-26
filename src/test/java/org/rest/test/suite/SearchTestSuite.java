package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.persistence.service.PrincipalServiceSearchPersistenceIntegrationTest;
import org.rest.sec.persistence.service.PrivilegeServiceSearchPersistenceIntegrationTest;
import org.rest.sec.persistence.service.RoleServiceSearchPersistenceIntegrationTest;
import org.rest.sec.util.ConstructQueryStringUnitTest;
import org.rest.sec.util.ParseQueryStringUnitTest;
import org.rest.sec.web.privilege.PrivilegeSearchRESTIntegrationTest;
import org.rest.sec.web.role.RoleSearchRESTIntegrationTest;

@RunWith( Suite.class )
@SuiteClasses( {// @formatter:off
    ParseQueryStringUnitTest.class,
    ConstructQueryStringUnitTest.class,

    PrincipalServiceSearchPersistenceIntegrationTest.class,
    RoleServiceSearchPersistenceIntegrationTest.class,
    PrivilegeServiceSearchPersistenceIntegrationTest.class,

    RoleSearchRESTIntegrationTest.class,
    PrivilegeSearchRESTIntegrationTest.class
}) // @formatter:on
public final class SearchTestSuite{
	//
}
