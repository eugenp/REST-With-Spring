package org.baeldung.rest.service.impl;

import org.apache.commons.lang3.tuple.Triple;
import org.baeldung.rest.common.persistence.service.AbstractService;
import org.baeldung.rest.common.search.ClientOperation;
import org.baeldung.rest.model.Privilege;
import org.baeldung.rest.persistence.dao.IPrivilegeJpaDao;
import org.baeldung.rest.service.IPrivilegeService;
import org.baeldung.rest.util.SearchUtilSec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PrivilegeServiceImpl extends AbstractService<Privilege>implements IPrivilegeService {

    @Autowired
    IPrivilegeJpaDao dao;

    public PrivilegeServiceImpl() {
        super(Privilege.class);
    }

    // API

    // find

    @Override
    public Privilege findByName(final String name) {
        return getDao().findByName(name);
    }

    // Spring

    @Override
    protected final IPrivilegeJpaDao getDao() {
        return dao;
    }

    @Override
    public Specification<Privilege> resolveConstraint(final Triple<String, ClientOperation, String> constraint) {
        return SearchUtilSec.resolveConstraint(constraint, Privilege.class);
    }

    @Override
    protected JpaSpecificationExecutor<Privilege> getSpecificationExecutor() {
        return dao;
    }

}
