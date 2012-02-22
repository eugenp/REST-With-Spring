package org.rest.sec.persistence.service.impl;

import org.rest.persistence.service.AbstractService;
import org.rest.sec.model.Role;
import org.rest.sec.persistence.dao.IPrivilegeJpaDAO;
import org.rest.sec.persistence.dao.IRoleJpaDAO;
import org.rest.sec.persistence.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends AbstractService< Role > implements IRoleService{
	
	@Autowired IRoleJpaDAO dao;
	@Autowired IPrivilegeJpaDAO associationDao;
	
	public RoleServiceImpl(){
		super( Role.class );
	}
	
	// API
	
	// get/find
	
	@Override
	public Role findByName( final String name ){
		return dao.findByName( name );
	}
	
	// create
	
	@Override
	public Role create( final Role entity ){
		/*final List< Privilege > associationsTemp = Lists.newArrayList( entity.getPrivileges() );
		entity.getPrivileges().clear();
		for( final Privilege privilege : associationsTemp ){
			entity.getPrivileges().add( associationDao.findByName( privilege.getName() ) );
		}*/
		
		return super.create( entity );
	}
	
	// Spring
	
	@Override
	protected final IRoleJpaDAO getDao(){
		return dao;
	}
	
}
