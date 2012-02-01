package org.rest.sec.persistence.service.user.impl;

import org.rest.persistence.service.AbstractService;
import org.rest.sec.model.User;
import org.rest.sec.persistence.dao.IUserJpaDAO;
import org.rest.sec.persistence.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends AbstractService< User > implements IUserService{
	
	@Autowired
	IUserJpaDAO dao;
	
	public UserServiceImpl(){
		super( User.class );
	}
	
	// API
	
	// Spring
	
	@Override
	protected final IUserJpaDAO getDao(){
		return dao;
	}
	
}
