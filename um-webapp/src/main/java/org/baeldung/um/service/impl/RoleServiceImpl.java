package org.baeldung.um.service.impl;

import org.apache.commons.lang3.tuple.Triple;
import org.baeldung.common.persistence.service.AbstractService;
import org.baeldung.common.search.ClientOperation;
import org.baeldung.um.persistence.dao.IRoleJpaDao;
import org.baeldung.um.persistence.model.Role;
import org.baeldung.um.persistence.util.SearchUtilSec;
import org.baeldung.um.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends AbstractService<Role>implements IRoleService {

    @Autowired
    IRoleJpaDao dao;

    public RoleServiceImpl() {
        super(Role.class);
    }

    // API

    // get/find

    @Override
    public Role findByName(final String name) {
        return getDao().findByName(name);
    }

    // create

    @Override
    public Role create(final Role entity) {
        /*
         * final long id = IdUtil.randomPositiveLong(); entity.setId( id );
         */

        /*
         * final List< Privilege > associationsTemp = Lists.newArrayList( entity.getPrivileges() ); entity.getPrivileges().clear(); for( final Privilege privilege : associationsTemp ){ entity.getPrivileges().add(
         * associationDao.findByName( privilege.getName() ) ); }
         */

        return super.create(entity);
    }

    // Spring

    @Override
    public Specification<Role> resolveConstraint(final Triple<String, ClientOperation, String> constraint) {
        return SearchUtilSec.resolveConstraint(constraint, Role.class);
    }

    @Override
    protected final IRoleJpaDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<Role> getSpecificationExecutor() {
        return dao;
    }

}
