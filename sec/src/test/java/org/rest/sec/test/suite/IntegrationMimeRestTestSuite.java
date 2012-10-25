package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.web.privilege.PrivilegeMimeRestLiveTest;
import org.rest.sec.web.role.RoleMimeRestLiveTest;
import org.rest.sec.web.user.UserMimeRestLiveTest;

@RunWith(Suite.class)
@SuiteClasses({ PrivilegeMimeRestLiveTest.class, RoleMimeRestLiveTest.class, UserMimeRestLiveTest.class })
public final class IntegrationMimeRestTestSuite {
    //
}
