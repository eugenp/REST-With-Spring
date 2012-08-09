package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.security.AuthenticationRESTIntegrationTest;
import org.rest.sec.web.privilege.PrivilegeLogicRESTIntegrationTest;
import org.rest.sec.web.privilege.PrivilegeSearchRESTIntegrationTest;
import org.rest.sec.web.role.RoleLogicRESTIntegrationTest;
import org.rest.sec.web.role.RoleSearchRESTIntegrationTest;
import org.rest.sec.web.user.UserLogicRESTIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({// @formatter:off
UserLogicRESTIntegrationTest.class, RoleLogicRESTIntegrationTest.class, PrivilegeLogicRESTIntegrationTest.class,

RoleSearchRESTIntegrationTest.class, PrivilegeSearchRESTIntegrationTest.class,

AuthenticationRESTIntegrationTest.class })
// @formatter:off
public final class IntegrationLogicRESTTestSuite {
    //
}
