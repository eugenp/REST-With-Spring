package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.client.template.test.AuthenticationClientRESTIntegrationTest;
import org.rest.sec.client.template.test.PrivilegeClientRESTIntegrationTest;
import org.rest.sec.client.template.test.RoleClientRESTIntegrationTest;
import org.rest.sec.client.template.test.UserClientRESTIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ UserClientRESTIntegrationTest.class, RoleClientRESTIntegrationTest.class, PrivilegeClientRESTIntegrationTest.class, AuthenticationClientRESTIntegrationTest.class })
public final class IntegrationClientRESTTestSuite {
    //
}
