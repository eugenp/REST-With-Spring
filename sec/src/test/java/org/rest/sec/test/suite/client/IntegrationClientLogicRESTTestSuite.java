package org.rest.sec.test.suite.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.client.template.test.AuthenticationClientRESTIntegrationTest;
import org.rest.sec.client.template.test.PrivilegeClientLogicRESTIntegrationTest;
import org.rest.sec.client.template.test.RoleClientLogicRESTIntegrationTest;
import org.rest.sec.client.template.test.UserClientLogicRESTIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
    UserClientLogicRESTIntegrationTest.class,
    RoleClientLogicRESTIntegrationTest.class,
    PrivilegeClientLogicRESTIntegrationTest.class,

    AuthenticationClientRESTIntegrationTest.class
})// @formatter:on
public final class IntegrationClientLogicRESTTestSuite {
    //
}
