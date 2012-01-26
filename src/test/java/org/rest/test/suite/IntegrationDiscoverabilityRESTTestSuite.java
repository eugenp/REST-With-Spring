package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.poc.web.bar.BarDiscoverabilityRESTIntegrationTest;
import org.rest.poc.web.foo.FooDiscoverabilityRESTIntegrationTest;

@RunWith( Suite.class )
@SuiteClasses( { BarDiscoverabilityRESTIntegrationTest.class, FooDiscoverabilityRESTIntegrationTest.class } )
public final class IntegrationDiscoverabilityRESTTestSuite{
	//
}
