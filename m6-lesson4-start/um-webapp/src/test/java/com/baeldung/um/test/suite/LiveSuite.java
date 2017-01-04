package com.baeldung.um.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.baeldung.um.security.SecurityRestLiveTest;
import com.baeldung.um.web.role.RoleSimpleLiveTest;

@RunWith(Suite.class)
@SuiteClasses({ // @formatter:off
    LiveLogicSuite.class,
    SecurityRestLiveTest.class,
    RoleSimpleLiveTest.class
})
// @formatter:on
public final class LiveSuite {
    //
}
