package org.rest.sec.persistence.service.impl;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.rest.persistence.service.AbstractServiceUnitTest;
import org.rest.sec.model.Principal;
import org.rest.sec.model.Role;
import org.rest.sec.persistence.dao.IPrincipalJpaDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class PrincipalServiceUnitTest extends AbstractServiceUnitTest<Principal> {

    PrincipalServiceImpl instance;

    private IPrincipalJpaDAO daoMock;

    public PrincipalServiceUnitTest() {
	super(Principal.class);
    }

    // fixtures

    @Override
    @Before
    public final void before() {
	instance = new PrincipalServiceImpl();

	daoMock = mock(IPrincipalJpaDAO.class);
	when(daoMock.save(any(Principal.class))).thenReturn(new Principal());
	when(daoMock.findAll()).thenReturn(Lists.<Principal> newArrayList());
	instance.dao = daoMock;
	super.before();
    }

    // get

    @Test
    public final void whenGetIsTriggered_thenNoException() {
	configureGet(1l);

	// When
	instance.findOne(1l);

	// Then
    }

    @Test
    public final void whenGetIsTriggered_thenEntityIsRetrieved() {
	configureGet(1l);
	// When
	instance.findOne(1l);

	// Then
	verify(daoMock).findOne(1l);
    }

    // mocking behavior

    final Principal configureGet(final long id) {
	final Principal entity = new Principal();
	entity.setId(id);
	when(daoMock.findOne(id)).thenReturn(entity);
	return entity;
    }

    // template method

    @Override
    protected final PrincipalServiceImpl getService() {
	return instance;
    }

    @Override
    protected final JpaRepository<Principal, Long> getDAOMock() {
	return daoMock;
    }

    @Override
    protected final Principal createNewEntity() {
	return this.createNewEntity(randomAlphabetic(8));
    }

    // util

    final Principal createNewEntity(final String username) {
	return new Principal(username, randomAlphabetic(8), Sets.<Role> newHashSet());
    }

}
