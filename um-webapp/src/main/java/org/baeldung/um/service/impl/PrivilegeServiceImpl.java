package org.baeldung.um.service.impl;

import org.apache.commons.lang3.tuple.Triple;
import org.baeldung.common.persistence.service.AbstractService;
import org.baeldung.common.search.ClientOperation;
import org.baeldung.um.persistence.dao.IPrivilegeJpaDao;
import org.baeldung.um.persistence.model.Privilege;
import org.baeldung.um.persistence.util.SearchUtilSec;
import org.baeldung.um.service.IPrivilegeService;
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
