package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.sec.persistence.dao.PrincipalDAOPersistenceIntegrationTest;
import org.rest.sec.persistence.dao.PrivilegeDAOPersistenceIntegrationTest;
import org.rest.sec.persistence.dao.RoleDAOPersistenceIntegrationTest;
import org.rest.sec.persistence.service.PrincipalSearchPersistenceIntegrationTest;
import org.rest.sec.persistence.service.PrincipalServicePersistenceIntegrationTest;
import org.rest.sec.persistence.service.PrivilegeSearchPersistenceIntegrationTest;
import org.rest.sec.persistence.service.PrivilegeServicePersistenceIntegrationTest;
import org.rest.sec.persistence.service.RoleSearchPersistenceIntegrationTest;
import org.rest.sec.persistence.service.RoleServicePersistenceIntegrationTest;

@RunWith( Suite.class )
@SuiteClasses( {// @formatter:off
    PrincipalSearchPersistenceIntegrationTest.class,
    PrincipalServicePersistenceIntegrationTest.class,
    PrincipalDAOPersistenceIntegrationTest.class,

    PrivilegeSearchPersistenceIntegrationTest.class,
    PrivilegeServicePersistenceIntegrationTest.class,
    PrivilegeDAOPersistenceIntegrationTest.class,

    RoleSearchPersistenceIntegrationTest.class,
    RoleServicePersistenceIntegrationTest.class,
    RoleDAOPersistenceIntegrationTest.class
} ) // @formatter:on
public final class IntegrationPersistenceTestSuite{
	//
}
