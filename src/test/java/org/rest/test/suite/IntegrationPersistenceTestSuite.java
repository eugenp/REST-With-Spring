package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.persistence.dao.PrincipalDAOPersistenceIntegrationTest;
import org.rest.sec.persistence.dao.PrivilegeDAOPersistenceIntegrationTest;
import org.rest.sec.persistence.dao.RoleDAOPersistenceIntegrationTest;
import org.rest.sec.persistence.service.PrincipalServicePersistenceIntegrationTest;
import org.rest.sec.persistence.service.PrivilegeServicePersistenceIntegrationTest;
import org.rest.sec.persistence.service.RoleServicePersistenceIntegrationTest;
import org.rest.sec.persistence.service.RoleServiceSearchPersistenceIntegrationTest;

@RunWith( Suite.class )
@SuiteClasses( {// @formatter:off
	PrincipalServicePersistenceIntegrationTest.class,
	// UserDAOPersistenceIntegrationTest.class,
	
	PrincipalDAOPersistenceIntegrationTest.class,
	
	PrivilegeServicePersistenceIntegrationTest.class,
	PrivilegeDAOPersistenceIntegrationTest.class,
	
	RoleServicePersistenceIntegrationTest.class,
	RoleServiceSearchPersistenceIntegrationTest.class,
	RoleDAOPersistenceIntegrationTest.class
} ) // @formatter:on
public final class IntegrationPersistenceTestSuite{
	//
}
