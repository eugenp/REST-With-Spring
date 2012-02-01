package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.persistence.dao.user.UserDAOPersistenceIntegrationTest;
import org.rest.sec.persistence.service.user.UserServicePersistenceIntegrationTest;

@RunWith( Suite.class )
@SuiteClasses( { UserServicePersistenceIntegrationTest.class, UserDAOPersistenceIntegrationTest.class } )
public final class IntegrationPersistenceTestSuite{
	//
}
