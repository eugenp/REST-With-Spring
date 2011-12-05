package org.rest.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.service.foo.impl.FooServiceUnitTest;

@RunWith( Suite.class )
@SuiteClasses( { FooServiceUnitTest.class, SpringTest.class } )
public final class UnitTestSuite{
	//
}
