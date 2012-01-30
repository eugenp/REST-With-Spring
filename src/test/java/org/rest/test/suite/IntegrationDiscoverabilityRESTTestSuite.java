package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.poc.web.privilege.PrivilegeDiscoverabilityRESTIntegrationTest;
import org.rest.poc.web.user.UserDiscoverabilityRESTIntegrationTest;

@RunWith( Suite.class )
@SuiteClasses( { PrivilegeDiscoverabilityRESTIntegrationTest.class, UserDiscoverabilityRESTIntegrationTest.class } )
public final class IntegrationDiscoverabilityRESTTestSuite{
	//
}
