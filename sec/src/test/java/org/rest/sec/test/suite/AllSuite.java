package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LiveSuite.class, PersistenceSuite.class, UnitSuite.class })
public final class AllSuite {
    //
}
