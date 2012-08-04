package org.rest.sec.persistence.service.impl;

import org.apache.commons.lang3.tuple.Triple;
import org.rest.common.persistence.service.AbstractService;
import org.rest.common.web.ClientOperation;
import org.rest.sec.model.Role;
import org.rest.sec.persistence.dao.IRoleJpaDAO;
import org.rest.sec.persistence.service.IRoleService;
import org.rest.sec.util.SearchSecUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends AbstractService<Role> implements IRoleService {

    @Autowired IRoleJpaDAO dao;

    public RoleServiceImpl() {
        super(Role.class);
    }

    // API

    // get/find

    @Override
    public Role findByName(final String name) {
        return dao.findByName(name);
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
    protected final IRoleJpaDAO getDao() {
        return dao;
    }

    @Override
    public Specification<Role> resolveConstraint(final Triple<String, ClientOperation, String> constraint) {
        return SearchSecUtil.resolveConstraint(constraint, Role.class);
    }

    @Override
    protected JpaSpecificationExecutor<Role> getSpecificationExecutor() {
        return dao;
    }
}
