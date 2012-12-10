package org.rest.sec.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.common.search.ConstructQueryStringUnitTest;
import org.rest.sec.persistence.service.impl.PrincipalServiceUnitTest;
import org.rest.sec.persistence.service.impl.PrivilegeServiceUnitTest;
import org.rest.sec.persistence.service.impl.RoleServiceUnitTest;

@RunWith(Suite.class)
@SuiteClasses({ PrincipalServiceUnitTest.class, RoleServiceUnitTest.class, PrivilegeServiceUnitTest.class, ConstructQueryStringUnitTest.class })
public final class UnitSuite {
    //
}
