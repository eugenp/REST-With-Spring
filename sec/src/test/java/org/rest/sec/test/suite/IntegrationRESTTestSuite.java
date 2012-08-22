package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.security.SecurityRESTIntegrationTest;
import org.rest.sec.test.suite.client.IntegrationClientRESTTestSuite;

@RunWith(Suite.class)
@SuiteClasses({// @formatter:off
    IntegrationDiscoverabilityRESTTestSuite.class,
    IntegrationLogicRESTTestSuite.class,
    IntegrationSearchRESTTestSuite.class,
    IntegrationSortAndPaginationRESTTestSuite.class,
    IntegrationMimeRESTTestSuite.class,
    SecurityRESTIntegrationTest.class,

    IntegrationClientRESTTestSuite.class
})
// @formatter:on
public final class IntegrationRESTTestSuite {
    //
}
