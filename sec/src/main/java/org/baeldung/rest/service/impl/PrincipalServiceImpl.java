package org.baeldung.rest.service.impl;

import org.apache.commons.lang3.tuple.Triple;
import org.baeldung.rest.common.persistence.service.AbstractService;
import org.baeldung.rest.common.search.ClientOperation;
import org.baeldung.rest.common.security.SpringSecurityUtil;
import org.baeldung.rest.model.Principal;
import org.baeldung.rest.persistence.dao.IPrincipalJpaDao;
import org.baeldung.rest.service.IPrincipalService;
import org.baeldung.rest.util.SearchUtilSec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PrincipalServiceImpl extends AbstractService<Principal>implements IPrincipalService {

    @Autowired
    IPrincipalJpaDao dao;

    public PrincipalServiceImpl() {
        super(Principal.class);
    }

    // API

    // find

    @Override
    @Transactional(readOnly = true)
    public Principal findByName(final String name) {
        return dao.findByName(name);
    }

    // other

    @Override
    @Transactional(readOnly = true)
    public Principal getCurrentPrincipal() {
        final String principalName = SpringSecurityUtil.getNameOfCurrentPrincipal();
        return getDao().findByName(principalName);
    }

    // Spring

    @Override
    protected final IPrincipalJpaDao getDao() {
        return dao;
    }

    @Override
    public Specification<Principal> resolveConstraint(final Triple<String, ClientOperation, String> constraint) {
        return SearchUtilSec.resolveConstraint(constraint, Principal.class);
    }

    @Override
    protected JpaSpecificationExecutor<Principal> getSpecificationExecutor() {
        return dao;
    }

}
