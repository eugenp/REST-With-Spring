package org.baeldung.um.service.impl;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.baeldung.test.common.service.AbstractServiceUnitTest;
import org.baeldung.um.common.FixtureEntityFactory;
import org.baeldung.um.persistence.dao.IRoleJpaDao;
import org.baeldung.um.persistence.model.Role;
import org.baeldung.um.service.impl.RoleServiceImpl;
import org.junit.Before;
import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.collect.Lists;

public class RoleServiceUnitTest extends AbstractServiceUnitTest<Role> {

    private RoleServiceImpl instance;

    private IRoleJpaDao daoMock;

    // fixtures

    @Override
    @Before
    public final void before() {
        instance = new RoleServiceImpl();

        daoMock = mock(IRoleJpaDao.class);
        when(daoMock.save(any(Role.class))).thenReturn(new Role());
        when(daoMock.findAll()).thenReturn(Lists.<Role> newArrayList());
        instance.dao = daoMock;
        super.before();
    }

    // get

    // mocking behavior

    @Override
    protected final Role configureGet(final long id) {
        final Role entity = createNewEntity();
        entity.setId(id);
        when(daoMock.findOne(id)).thenReturn(entity);
        return entity;
    }

    // template method

    @Override
    protected final RoleServiceImpl getApi() {
        return instance;
    }

    @Override
    protected final JpaRepository<Role, Long> getDAO() {
        return daoMock;
    }

    @Override
    protected final Role createNewEntity() {
        return FixtureEntityFactory.createNewRole();
    }

    @Override
    protected void changeEntity(final Role entity) {
        entity.setName(randomAlphabetic(6));
    }

}
