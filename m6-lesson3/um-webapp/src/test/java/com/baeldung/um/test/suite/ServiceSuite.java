package com.baeldung.um.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.baeldung.um.service.main.UserServiceIntegrationTest;
import com.baeldung.um.service.main.PrivilegeServiceIntegrationTest;
import com.baeldung.um.service.main.RoleServiceIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ // @formatter:off
    UserServiceIntegrationTest.class,
    PrivilegeServiceIntegrationTest.class,
    RoleServiceIntegrationTest.class
})
// @formatter:on
public final class ServiceSuite {
    //
}
