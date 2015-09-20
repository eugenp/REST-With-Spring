package org.baeldung.um.service.impl;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.baeldung.test.common.service.AbstractServiceUnitTest;
import org.baeldung.test.common.util.IDUtil;
import org.baeldung.um.common.FixtureEntityFactory;
import org.baeldung.um.persistence.dao.IPrivilegeJpaDao;
import org.baeldung.um.persistence.model.Privilege;
import org.baeldung.um.service.impl.PrivilegeServiceImpl;
import org.junit.Before;
import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.collect.Lists;

public class PrivilegeServiceUnitTest extends AbstractServiceUnitTest<Privilege> {

    private PrivilegeServiceImpl instance;

    private IPrivilegeJpaDao daoMock;

    // fixtures

    @Override
    @Before
    public final void before() {
        instance = new PrivilegeServiceImpl();

        daoMock = mock(IPrivilegeJpaDao.class);
        when(daoMock.save(any(Privilege.class))).thenReturn(new Privilege());
        when(daoMock.findAll()).thenReturn(Lists.<Privilege> newArrayList());
        instance.dao = daoMock;
        super.before();
    }

    // get

    // mocking behavior

    @Override
    protected final Privilege configureGet(final long id) {
        final Privilege entity = new Privilege();
        entity.setId(id);
        when(daoMock.findOne(id)).thenReturn(entity);
        return entity;
    }

    // template method

    @Override
    protected final PrivilegeServiceImpl getApi() {
        return instance;
    }

    @Override
    protected final JpaRepository<Privilege, Long> getDAO() {
        return daoMock;
    }

    @Override
    protected Privilege createNewEntity() {
        final Privilege newPrivilege = FixtureEntityFactory.createNewPrivilege();
        newPrivilege.setId(IDUtil.randomPositiveLong());
        return newPrivilege;
    }

    @Override
    protected void changeEntity(final Privilege entity) {
        entity.setName(randomAlphabetic(8));
    }

}
