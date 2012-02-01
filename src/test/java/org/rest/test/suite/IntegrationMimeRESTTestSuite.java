package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.web.privilege.PrivilegeMimeRESTIntegrationTest;
import org.rest.sec.web.user.UserMimeRESTIntegrationTest;

@RunWith( Suite.class )
@SuiteClasses( { UserMimeRESTIntegrationTest.class, PrivilegeMimeRESTIntegrationTest.class } )
public final class IntegrationMimeRESTTestSuite{
	//
}
