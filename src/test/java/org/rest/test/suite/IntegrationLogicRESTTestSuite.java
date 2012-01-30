package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.poc.web.privilege.PrivilegeLogicRESTIntegrationTest;
import org.rest.poc.web.user.UserLogicRESTIntegrationTest;

@RunWith( Suite.class )
@SuiteClasses( { UserLogicRESTIntegrationTest.class, PrivilegeLogicRESTIntegrationTest.class } )
public final class IntegrationLogicRESTTestSuite{
	//
}
