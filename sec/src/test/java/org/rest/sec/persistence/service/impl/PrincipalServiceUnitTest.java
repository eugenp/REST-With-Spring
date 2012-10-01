package org.rest.sec.persistence.service.impl;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.rest.common.persistence.service.AbstractServiceUnitTest;
import org.rest.sec.model.Principal;
import org.rest.sec.persistence.dao.IPrincipalJpaDAO;
import org.rest.sec.persistence.util.FixtureFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.collect.Lists;

public class PrincipalServiceUnitTest extends AbstractServiceUnitTest<Principal> {

    PrincipalServiceImpl instance;

    private IPrincipalJpaDAO daoMock;

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

    // mocking behavior

    @Override
    protected final Principal configureGet(final long id) {
        final Principal entity = new Principal();
        entity.setId(id);
        when(daoMock.findOne(id)).thenReturn(entity);
        return entity;
    }

    // template method

    @Override
    protected final PrincipalServiceImpl getApi() {
        return instance;
    }

    @Override
    protected final JpaRepository<Principal, Long> getDAO() {
        return daoMock;
    }

    @Override
    protected final Principal createNewEntity() {
        return FixtureFactory.createNewPrincipal();
    }

    @Override
    protected void changeEntity(final Principal entity) {
        entity.setPassword(randomAlphabetic(8));
    }

}
