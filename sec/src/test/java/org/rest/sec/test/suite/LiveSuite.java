package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.security.SecurityRestLiveTest;
import org.rest.sec.test.suite.client.LiveClientSuite;

@RunWith(Suite.class)
@SuiteClasses({// @formatter:off
    LiveDiscoverabilitySuite.class,
    LiveLogicSuite.class,
    LiveSearchSuite.class,
    SecurityRestLiveTest.class,

    LiveClientSuite.class
})
// @formatter:on
public final class LiveSuite {
    //
}
