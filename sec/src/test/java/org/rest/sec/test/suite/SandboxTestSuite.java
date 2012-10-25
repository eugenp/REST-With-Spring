package org.rest.sec.test.suite;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.web.role.RoleLogicRestLiveTest;

@RunWith(Suite.class)
@SuiteClasses({ RoleLogicRestLiveTest.class })
@Ignore
public final class SandboxTestSuite {
    //
}
