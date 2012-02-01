package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.persistence.service.user.impl.UserServiceUnitTest;

@RunWith( Suite.class )
@SuiteClasses( { UserServiceUnitTest.class } )
public final class UnitTestSuite{
	//
}
