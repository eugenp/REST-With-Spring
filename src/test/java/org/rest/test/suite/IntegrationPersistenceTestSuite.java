package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.persistence.dao.PrivilegeDAOPersistenceIntegrationTest;
import org.rest.sec.persistence.dao.RoleDAOPersistenceIntegrationTest;
import org.rest.sec.persistence.dao.UserDAOPersistenceIntegrationTest;
import org.rest.sec.persistence.service.PrivilegeServicePersistenceIntegrationTest;
import org.rest.sec.persistence.service.RoleServicePersistenceIntegrationTest;
import org.rest.sec.persistence.service.UserServicePersistenceIntegrationTest;

@RunWith( Suite.class )
@SuiteClasses( { UserServicePersistenceIntegrationTest.class, UserDAOPersistenceIntegrationTest.class, PrivilegeServicePersistenceIntegrationTest.class, PrivilegeDAOPersistenceIntegrationTest.class, RoleServicePersistenceIntegrationTest.class, RoleDAOPersistenceIntegrationTest.class } )
public final class IntegrationPersistenceTestSuite{
	//
}
