package org.rest.poc.persistence.service.user.impl;

import org.rest.persistence.service.AbstractService;
import org.rest.poc.model.User;
import org.rest.poc.persistence.dao.IUserJpaDAO;
import org.rest.poc.persistence.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends AbstractService< User > implements IUserService{
	
	public UserServiceImpl(){
		super( User.class );
	}
	
	@Autowired
	IUserJpaDAO dao;
	
	// API
	
	// get/find
	
	@Override
	@Transactional( readOnly = true )
	public Page< User > findPaginated( final int page, final int size ){
		return dao.findAll( new PageRequest( page, size ) );
	}
	
	// Spring
	
	@Override
	protected final IUserJpaDAO getDao(){
		return dao;
	}
	
}
