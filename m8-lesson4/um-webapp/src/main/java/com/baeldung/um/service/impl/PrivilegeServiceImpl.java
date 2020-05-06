package com.baeldung.um.service.impl;

import io.micrometer.core.instrument.Counter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.common.persistence.service.AbstractService;
import com.baeldung.um.persistence.dao.IPrivilegeJpaDao;
import com.baeldung.um.persistence.model.Privilege;
import com.baeldung.um.service.IPrivilegeService;

import org.slf4j.Logger;

@Service
@Transactional
public class PrivilegeServiceImpl extends AbstractService<Privilege> implements IPrivilegeService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IPrivilegeJpaDao dao;

    @Autowired
    @Qualifier("privilegeCounter")
    private Counter privilegeCounter;

    public PrivilegeServiceImpl() {
        super(Privilege.class);
    }

    // API

    // find

    @Override
    public Privilege findByName(final String name) {
        logger.info("[privilege] increment findByName by 1, name = {}", name);
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
