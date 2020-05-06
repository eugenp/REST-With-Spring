package com.baeldung.um.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.common.persistence.service.AbstractService;
import com.baeldung.um.persistence.dao.IPrivilegeJpaDao;
import com.baeldung.um.persistence.model.Privilege;
import com.baeldung.um.service.IPrivilegeService;

import io.micrometer.core.instrument.Counter;

@Service
@Transactional
public class PrivilegeServiceImpl extends AbstractService<Privilege> implements IPrivilegeService {

    @Autowired
    IPrivilegeJpaDao dao;

    @Autowired
    private Counter privilegeCounter;

    public PrivilegeServiceImpl() {
        super(Privilege.class);
    }

    // API

    // find

    @Override
    public Privilege findByName(final String name) {
        privilegeCounter.increment();

        return getDao().findByName(name);
    }

    // Spring

    @Override
    protected final IPrivilegeJpaDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<Privilege> getSpecificationExecutor() {
        return dao;
    }

}
