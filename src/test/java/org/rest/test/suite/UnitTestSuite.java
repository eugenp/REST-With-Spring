package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.persistence.service.impl.PrivilegeServiceUnitTest;
import org.rest.sec.persistence.service.impl.RoleServiceUnitTest;
import org.rest.sec.persistence.service.impl.PrincipalServiceUnitTest;

@RunWith( Suite.class )
@SuiteClasses( { PrincipalServiceUnitTest.class, RoleServiceUnitTest.class, PrivilegeServiceUnitTest.class } )
public final class UnitTestSuite{
	//
}
