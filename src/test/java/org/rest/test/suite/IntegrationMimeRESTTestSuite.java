package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.poc.web.bar.BarMimeRESTIntegrationTest;
import org.rest.poc.web.foo.FooMimeRESTIntegrationTest;

@RunWith( Suite.class )
@SuiteClasses( { FooMimeRESTIntegrationTest.class, BarMimeRESTIntegrationTest.class } )
public final class IntegrationMimeRESTTestSuite{
	//
}
