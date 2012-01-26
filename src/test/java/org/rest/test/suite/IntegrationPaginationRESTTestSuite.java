package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.poc.web.bar.BarPaginationRESTIntegrationTest;
import org.rest.poc.web.foo.FooPaginationRESTIntegrationTest;

@RunWith( Suite.class )
@SuiteClasses( { FooPaginationRESTIntegrationTest.class, BarPaginationRESTIntegrationTest.class } )
public final class IntegrationPaginationRESTTestSuite{
	//
}
