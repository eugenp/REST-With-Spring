package org.rest.sec.persistence.service.impl;

import org.rest.persistence.service.AbstractService;
import org.rest.sec.model.Principal;
import org.rest.sec.persistence.dao.IPrincipalJpaDAO;
import org.rest.sec.persistence.service.IPrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PrincipalServiceImpl extends AbstractService< Principal > implements IPrincipalService{
	
	@Autowired IPrincipalJpaDAO dao;
	
	public PrincipalServiceImpl(){
		super( Principal.class );
	}
	
	// API
	
	@Override
	@Transactional( readOnly = true )
	public Principal findByName( final String name ){
		return dao.findByName( name );
	}
	
	// Spring
	
	@Override
	protected final IPrincipalJpaDAO getDao(){
		return dao;
	}
	
}
