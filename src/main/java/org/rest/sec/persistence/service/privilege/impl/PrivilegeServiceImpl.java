package org.rest.sec.persistence.service.privilege.impl;

import org.rest.persistence.service.AbstractService;
import org.rest.sec.model.Privilege;
import org.rest.sec.persistence.dao.IPrivilegeJpaDAO;
import org.rest.sec.persistence.service.privilege.IPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PrivilegeServiceImpl extends AbstractService< Privilege > implements IPrivilegeService{
	
	@Autowired
	IPrivilegeJpaDAO dao;
	
	public PrivilegeServiceImpl(){
		super( Privilege.class );
	}
	
	// API
	
	// Spring
	
	@Override
	protected final IPrivilegeJpaDAO getDao(){
		return dao;
	}
	
}
