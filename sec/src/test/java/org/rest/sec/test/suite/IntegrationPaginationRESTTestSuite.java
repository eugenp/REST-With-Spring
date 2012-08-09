package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.web.privilege.PrivilegePaginationRESTIntegrationTest;
import org.rest.sec.web.role.RolePaginationRESTIntegrationTest;
import org.rest.sec.web.user.UserPaginationRESTIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ PrivilegePaginationRESTIntegrationTest.class, RolePaginationRESTIntegrationTest.class, UserPaginationRESTIntegrationTest.class })
public final class IntegrationPaginationRESTTestSuite {
    //
}
