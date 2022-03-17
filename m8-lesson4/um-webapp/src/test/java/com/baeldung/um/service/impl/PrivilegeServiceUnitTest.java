package com.baeldung.um.service.impl;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.test.common.service.AbstractServiceUnitTest;
import com.baeldung.test.common.util.IDUtil;
import com.baeldung.um.common.FixtureEntityFactory;
import com.baeldung.um.persistence.dao.IPrivilegeJpaDao;
import com.baeldung.um.persistence.model.Privilege;
import com.baeldung.um.service.impl.PrivilegeServiceImpl;
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
        when(daoMock.findById(id)).thenReturn(Optional.of(entity));
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
