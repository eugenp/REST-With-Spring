package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.security.SecurityRestLiveTest;
import org.rest.sec.test.suite.client.IntegrationClientRestTestSuite;

@RunWith(Suite.class)
@SuiteClasses({// @formatter:off
    IntegrationDiscoverabilityRestTestSuite.class,
    IntegrationLogicRestTestSuite.class,
    IntegrationSearchRestTestSuite.class,
    IntegrationSortAndPaginationRestTestSuite.class,
    IntegrationMimeRestTestSuite.class,
    SecurityRestLiveTest.class,

    IntegrationClientRestTestSuite.class
})
// @formatter:on
public final class IntegrationRestTestSuite {
    //
}
