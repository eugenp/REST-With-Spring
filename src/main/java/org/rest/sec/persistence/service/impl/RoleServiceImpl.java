package org.rest.sec.persistence.service.impl;

import org.rest.persistence.service.AbstractService;
import org.rest.sec.model.Role;
import org.rest.sec.persistence.dao.IRoleJpaDAO;
import org.rest.sec.persistence.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends AbstractService< Role > implements IRoleService{
	
	@Autowired
	IRoleJpaDAO dao;
	
	public RoleServiceImpl(){
		super();
	}
	
	// API
	
	// get/find
	
	@Override
	public Role findByName( final String name ){
		return dao.findByName( name );
	}
	
	// Spring
	
	@Override
	protected final IRoleJpaDAO getDao(){
		return dao;
	}
	
}
