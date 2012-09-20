package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.web.privilege.PrivilegeMimeRestIntegrationTest;
import org.rest.sec.web.role.RoleMimeRestIntegrationTest;
import org.rest.sec.web.user.UserMimeRestIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ PrivilegeMimeRestIntegrationTest.class, RoleMimeRestIntegrationTest.class, UserMimeRestIntegrationTest.class })
public final class IntegrationMimeRestTestSuite {
    //
}
