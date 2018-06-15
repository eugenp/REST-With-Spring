package com.baeldung.um.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.baeldung.common.web.listeners.PaginatedResultsRetrievedDiscoverabilityListenerUnitTest;
import com.baeldung.um.service.impl.PrincipalServiceUnitTest;
import com.baeldung.um.service.impl.PrivilegeServiceUnitTest;
import com.baeldung.um.service.impl.RoleServiceUnitTest;

@RunWith(Suite.class)
@SuiteClasses({ // @formatter:off
    PrincipalServiceUnitTest.class
    ,RoleServiceUnitTest.class
    ,PrivilegeServiceUnitTest.class
    ,PaginatedResultsRetrievedDiscoverabilityListenerUnitTest.class
    // ,Base64UnitTest.class
    // ,ParseQueryStringUnitTest.class
    // ,UriUnitTest.class
})// @formatter:on
public final class UnitSuite {
    //
}
