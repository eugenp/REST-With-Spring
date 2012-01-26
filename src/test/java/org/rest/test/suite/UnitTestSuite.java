package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.poc.persistence.service.foo.impl.FooServiceUnitTest;

@RunWith( Suite.class )
@SuiteClasses( { FooServiceUnitTest.class } )
public final class UnitTestSuite{
	//
}
