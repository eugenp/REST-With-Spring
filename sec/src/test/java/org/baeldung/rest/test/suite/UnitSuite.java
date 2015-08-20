package org.baeldung.rest.test.suite;

import org.baeldung.rest.common.search.ConstructQueryStringUnitTest;
import org.baeldung.rest.service.impl.PrincipalServiceUnitTest;
import org.baeldung.rest.service.impl.PrivilegeServiceUnitTest;
import org.baeldung.rest.service.impl.RoleServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PrincipalServiceUnitTest.class, RoleServiceUnitTest.class, PrivilegeServiceUnitTest.class, ConstructQueryStringUnitTest.class })
public final class UnitSuite {
    //
}
