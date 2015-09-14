package org.baeldung.rest.test.suite.client;

import org.baeldung.rest.client.AuthenticationClientRestLiveTest;
import org.baeldung.rest.client.PrivilegeLogicClientRestLiveTest;
import org.baeldung.rest.client.PrivilegeReadOnlyLogicClientRestLiveTest;
import org.baeldung.rest.client.RoleLogicClientRestLiveTest;
import org.baeldung.rest.client.RoleReadOnlyLogicClientRestLiveTest;
import org.baeldung.rest.client.UserLogicClientRestLiveTest;
import org.baeldung.rest.client.UserReadOnlyLogicClientRestLiveTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ //@formatter:off
    UserLogicClientRestLiveTest.class,
    RoleLogicClientRestLiveTest.class,
    PrivilegeLogicClientRestLiveTest.class,

    UserReadOnlyLogicClientRestLiveTest.class,
    RoleReadOnlyLogicClientRestLiveTest.class,
    PrivilegeReadOnlyLogicClientRestLiveTest.class,

    AuthenticationClientRestLiveTest.class
})// @formatter:on
public final class LiveClientLogicSuite {
    //
}
