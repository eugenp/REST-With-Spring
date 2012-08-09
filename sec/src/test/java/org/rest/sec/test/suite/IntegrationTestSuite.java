package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ IntegrationPersistenceTestSuite.class, IntegrationRESTTestSuite.class, SpringIntegrationTest.class, UnitTestSuite.class })
public final class IntegrationTestSuite {
    //
}
