package com.baeldung.um.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LiveSuite.class, ServiceSuite.class, UnitSuite.class })
public final class AllSuite {
    // 293 tests, 1 error 
}
