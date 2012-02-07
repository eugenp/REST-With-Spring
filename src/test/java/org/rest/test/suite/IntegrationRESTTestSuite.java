package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.security.SecurityRESTIntegrationTest;

@RunWith( Suite.class )
@SuiteClasses( { SecurityRESTIntegrationTest.class, IntegrationDiscoverabilityRESTTestSuite.class, IntegrationLogicRESTTestSuite.class, IntegrationPaginationRESTTestSuite.class, IntegrationSortRESTTestSuite.class, IntegrationMimeRESTTestSuite.class } )
public final class IntegrationRESTTestSuite{
	//
}
