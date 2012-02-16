package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.web.privilege.PrivilegeLogicRESTIntegrationTest;
import org.rest.sec.web.role.RoleLogicRESTIntegrationTest;
import org.rest.sec.web.user.UserLogicRESTIntegrationTest;
import org.rest.security.AuthenticationRESTIntegrationTest;

@RunWith( Suite.class )
@SuiteClasses( { PrivilegeLogicRESTIntegrationTest.class, RoleLogicRESTIntegrationTest.class, UserLogicRESTIntegrationTest.class, AuthenticationRESTIntegrationTest.class } )
public final class IntegrationLogicRESTTestSuite{
	//
}
