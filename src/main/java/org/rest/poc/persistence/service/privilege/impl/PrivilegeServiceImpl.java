package org.rest.poc.persistence.service.privilege.impl;

import org.rest.persistence.service.AbstractService;
import org.rest.poc.model.Privilege;
import org.rest.poc.persistence.dao.IPrivilegeJpaDAO;
import org.rest.poc.persistence.service.privilege.IPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PrivilegeServiceImpl extends AbstractService< Privilege > implements IPrivilegeService{
	
	public PrivilegeServiceImpl(){
		super( Privilege.class );
	}
	
	@Autowired
	IPrivilegeJpaDAO dao;
	
	// API
	
	@Override
	public Page< Privilege > findPaginated( final int page, final int size ){
		return dao.findAll( new PageRequest( page, size ) );
	}
	
	// Spring
	
	@Override
	protected final IPrivilegeJpaDAO getDao(){
		return dao;
	}
	
}
