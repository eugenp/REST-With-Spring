package org.rest.sec.test.suite.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.client.template.test.AuthenticationClientRestIntegrationTest;
import org.rest.sec.client.template.test.PrivilegeLogicClientRestIntegrationTest;
import org.rest.sec.client.template.test.RoleLogicClientRestIntegrationTest;
import org.rest.sec.client.template.test.UserLogicClientRestIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
    UserLogicClientRestIntegrationTest.class,
    RoleLogicClientRestIntegrationTest.class,
    PrivilegeLogicClientRestIntegrationTest.class,

    AuthenticationClientRestIntegrationTest.class
})// @formatter:on
public final class IntegrationClientLogicRestTestSuite {
    //
}
