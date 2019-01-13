package com.baeldung.um.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.baeldung.um.security.SecurityRestLiveTest;

@RunWith(Suite.class)
@SuiteClasses({ // @formatter:off
    LiveLogicSuite.class,
    SecurityRestLiveTest.class,
})
// @formatter:on
public final class LiveSuite {
    //
}
