package org.baeldung.um.test.suite;

import org.baeldung.um.security.SecurityRestLiveTest;
import org.baeldung.um.test.suite.client.LiveClientSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ // @formatter:off
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
