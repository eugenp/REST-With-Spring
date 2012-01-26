package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.poc.web.bar.BarLogicRESTIntegrationTest;
import org.rest.poc.web.foo.FooLogicRESTIntegrationTest;

@RunWith( Suite.class )
@SuiteClasses( { FooLogicRESTIntegrationTest.class, BarLogicRESTIntegrationTest.class } )
public final class IntegrationLogicRESTTestSuite{
	//
}
