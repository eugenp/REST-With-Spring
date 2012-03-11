package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.security.SecurityRESTIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({// @formatter:off
	IntegrationClientRESTTestSuite.class,
	IntegrationDiscoverabilityRESTTestSuite.class,
	IntegrationLogicRESTTestSuite.class,
	IntegrationPaginationRESTTestSuite.class,
	IntegrationSortRESTTestSuite.class,
	IntegrationMimeRESTTestSuite.class,
	SecurityRESTIntegrationTest.class
} ) // @formatter:on
public final class IntegrationRESTTestSuite {
    //
}
