package org.rest.sec.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.persistence.AbstractPersistenceServiceIntegrationTest;
import org.rest.persistence.service.IService;
import org.rest.sec.model.Principal;
import org.rest.sec.model.Role;
import org.rest.spring.context.ContextTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.spring.testing.TestingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.common.collect.Sets;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestingConfig.class, PersistenceJPAConfig.class, ContextTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class PrincipalServicePersistenceIntegrationTest extends AbstractPersistenceServiceIntegrationTest<Principal> {

    @Autowired
    IPrivilegeService privilegeService;
    @Autowired
    IRoleService roleService;
    @Autowired
    private IPrincipalService principalService;

    // create

    @Test
    public void whenSaveIsPerformed_thenNoException() {
	getAPI().create(createNewEntity());
    }

    @Test(expected = DataAccessException.class)
    public void whenAUniqueConstraintIsBroken_thenSpringSpecificExceptionIsThrown() {
	final String name = randomAlphabetic(8);

	getAPI().create(createNewEntity(name));
	getAPI().create(createNewEntity(name));
    }

    // Spring

    @Override
    protected final IService<Principal> getAPI() {
	return principalService;
    }

    // template method

    @Override
    protected final Principal createNewEntity() {
	return new Principal(randomAlphabetic(8), randomAlphabetic(8), Sets.<Role> newHashSet());
    }

    protected final Principal createNewEntity(final String name) {
	return new Principal(name, randomAlphabetic(8), Sets.<Role> newHashSet());
    }

    @Override
    protected final void invalidate(final Principal entity) {
	entity.setName(null);
    }

    @Override
    protected void changeEntity(final Principal entity) {
	entity.setPassword(randomAlphabetic(8));
    }

}
