package org.rest.sec.persistence.service;

import org.junit.runner.RunWith;
import org.rest.persistence.AbstractServiceSearchPersistenceIntegrationTest;
import org.rest.sec.model.Role;
import org.rest.sec.persistence.util.FixtureFactory;
import org.rest.spring.context.ContextTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.spring.testing.TestingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestingConfig.class, PersistenceJPAConfig.class, ContextTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class RoleServiceSearchPersistenceIntegrationTest extends AbstractServiceSearchPersistenceIntegrationTest<Role> {

    @Autowired
    private IRoleService roleService;

    // tests

    // template method

    @Override
    protected final IRoleService getService() {
	return roleService;
    }

    @Override
    protected final Role createNewEntity() {
	return FixtureFactory.createNewRole();
    }

}
