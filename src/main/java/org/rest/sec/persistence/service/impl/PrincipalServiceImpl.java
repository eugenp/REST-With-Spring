package org.rest.sec.persistence.service.impl;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.rest.persistence.service.AbstractService;
import org.rest.sec.model.Principal;
import org.rest.sec.persistence.dao.IPrincipalJpaDAO;
import org.rest.sec.persistence.service.IPrincipalService;
import org.rest.sec.util.SearchSecUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional
public class PrincipalServiceImpl extends AbstractService<Principal> implements IPrincipalService {

    @Autowired
    IPrincipalJpaDAO dao;

    public PrincipalServiceImpl() {
	super(Principal.class);
    }

    // API

    // search

    @Override
    public List<Principal> search(final ImmutablePair<String, ?>... constraints) {
	final Specification<Principal> firstSpec = SearchSecUtil.resolveConstraint(constraints[0], Principal.class);
	Specifications<Principal> specifications = Specifications.where(firstSpec);
	for (int i = 1; i < constraints.length; i++) {
	    specifications = specifications.and(SearchSecUtil.resolveConstraint(constraints[i], Principal.class));
	}
	if (firstSpec == null) {
	    return Lists.newArrayList();
	}

	return getDao().findAll(specifications);
    }

    // find

    @Override
    @Transactional(readOnly = true)
    public Principal findByName(final String name) {
	return dao.findByName(name);
    }

    // Spring

    @Override
    protected final IPrincipalJpaDAO getDao() {
	return dao;
    }

}
