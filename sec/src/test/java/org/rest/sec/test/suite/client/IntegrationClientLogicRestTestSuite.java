package org.rest.sec.test.suite.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.client.template.test.AuthenticationClientRestIntegrationTest;
import org.rest.sec.client.template.test.PrivilegeClientLogicRestIntegrationTest;
import org.rest.sec.client.template.test.RoleClientLogicRestIntegrationTest;
import org.rest.sec.client.template.test.UserClientLogicRestIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
    UserClientLogicRestIntegrationTest.class,
    RoleClientLogicRestIntegrationTest.class,
    PrivilegeClientLogicRestIntegrationTest.class,

    AuthenticationClientRestIntegrationTest.class
})// @formatter:on
public final class IntegrationClientLogicRestTestSuite {
    //
}
