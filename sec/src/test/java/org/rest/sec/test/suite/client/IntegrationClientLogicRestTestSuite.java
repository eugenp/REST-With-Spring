package org.rest.sec.test.suite.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.client.template.test.AuthenticationClientRestIntegrationTest;
import org.rest.sec.client.template.test.PrivilegeLogicClientRestLiveTest;
import org.rest.sec.client.template.test.RoleLogicClientRestLiveTest;
import org.rest.sec.client.template.test.UserLogicClientRestLiveTest;

@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
    UserLogicClientRestLiveTest.class,
    RoleLogicClientRestLiveTest.class,
    PrivilegeLogicClientRestLiveTest.class,

    AuthenticationClientRestIntegrationTest.class
})// @formatter:on
public final class IntegrationClientLogicRestTestSuite {
    //
}
