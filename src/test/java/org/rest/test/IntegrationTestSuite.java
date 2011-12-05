package org.rest.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.integration.foo.FooRESTDiscoverabilityIntegrationTest;
import org.rest.integration.foo.FooRESTIntegrationTest;
import org.rest.integration.foo.FooRESTMimeIntegrationTest;
import org.rest.integration.security.SecurityIntegrationTest;

@RunWith( Suite.class )
@SuiteClasses( { SecurityIntegrationTest.class, FooRESTDiscoverabilityIntegrationTest.class, FooRESTIntegrationTest.class, FooRESTMimeIntegrationTest.class } )
public final class IntegrationTestSuite{
	//
}
