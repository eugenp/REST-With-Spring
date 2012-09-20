package org.rest.sec.test.suite;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.web.role.RoleLogicRestIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ RoleLogicRestIntegrationTest.class })
@Ignore
public final class SandboxTestSuite {
    //
}
