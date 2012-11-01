package org.rest.sec.test.suite.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.client.template.test.AuthenticationClientRestLiveTest;
import org.rest.sec.client.template.test.PrivilegeLogicClientRestLiveTest;
import org.rest.sec.client.template.test.RoleLogicClientRestLiveTest;
import org.rest.sec.client.template.test.UserLogicClientRestLiveTest;

@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
    UserLogicClientRestLiveTest.class,
    RoleLogicClientRestLiveTest.class,
    PrivilegeLogicClientRestLiveTest.class,

    AuthenticationClientRestLiveTest.class
})// @formatter:on
public final class IntegrationClientLogicRestTestSuite {
    //
}
