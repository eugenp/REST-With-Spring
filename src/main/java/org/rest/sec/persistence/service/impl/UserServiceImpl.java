package org.rest.sec.persistence.service.impl;

import org.rest.persistence.service.AbstractService;
import org.rest.sec.model.User;
import org.rest.sec.persistence.dao.IUserJpaDAO;
import org.rest.sec.persistence.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends AbstractService< User > implements IUserService{
	
	@Autowired
	IUserJpaDAO dao;
	
	public UserServiceImpl(){
		super();
	}
	
	// API
	
	@Override
	public User findByName( final String name ){
		return dao.findByName( name );
	}

	// Spring
	
	@Override
	protected final IUserJpaDAO getDao(){
		return dao;
	}
	
}
