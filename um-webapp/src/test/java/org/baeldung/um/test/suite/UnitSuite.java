package org.baeldung.um.test.suite;

import org.baeldung.um.common.search.ConstructQueryStringUnitTest;
import org.baeldung.um.service.impl.PrincipalServiceUnitTest;
import org.baeldung.um.service.impl.PrivilegeServiceUnitTest;
import org.baeldung.um.service.impl.RoleServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PrincipalServiceUnitTest.class, RoleServiceUnitTest.class, PrivilegeServiceUnitTest.class, ConstructQueryStringUnitTest.class })
public final class UnitSuite {
    //
}
