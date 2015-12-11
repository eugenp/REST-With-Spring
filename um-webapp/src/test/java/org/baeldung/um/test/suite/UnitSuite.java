package org.baeldung.um.test.suite;

import org.baeldung.common.web.listeners.PaginatedResultsRetrievedDiscoverabilityListenerUnitTest;
import org.baeldung.um.common.search.ConstructQueryStringUnitTest;
import org.baeldung.um.service.impl.PrincipalServiceUnitTest;
import org.baeldung.um.service.impl.PrivilegeServiceUnitTest;
import org.baeldung.um.service.impl.RoleServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ // @formatter:off
    PrincipalServiceUnitTest.class
    ,RoleServiceUnitTest.class
    ,PrivilegeServiceUnitTest.class
    ,ConstructQueryStringUnitTest.class
    ,PaginatedResultsRetrievedDiscoverabilityListenerUnitTest.class
    // ,Base64UnitTest.class
    // ,ParseQueryStringUnitTest.class
    // ,UriUnitTest.class
})// @formatter:on
public final class UnitSuite {
    //
}
