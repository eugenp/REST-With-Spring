package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ IntegrationRestTestSuite.class, IntegrationPersistenceTestSuite.class, UnitTestSuite.class })
public final class AllTestSuite {
    //
}
