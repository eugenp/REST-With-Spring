package org.rest.sec.persistence.service.impl.dto;

import java.util.List;

import org.rest.sec.dto.User;
import org.rest.sec.model.Principal;
import org.rest.sec.persistence.service.IPrincipalService;
import org.rest.sec.persistence.service.dto.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements IUserService{
	
	@Autowired
	IPrincipalService principalService;
	
	public UserServiceImpl(){
		super();
	}
	
	// API
	
	@Override
	public User findByName( final String name ){
		final Principal principal = principalService.findByName( name );
		return new User( principal );
	}
	
	@Override
	public User findOne( final long id ){
		return null;
	}
	
	@Override
	public List< User > findAll(){
		return null;
	}
	
	@Override
	public Page< User > findPaginated( final int page, final int size, final String sortBy ){
		return null;
	}
	
	@Override
	public User create( final User entity ){
		return null;
	}
	
	@Override
	public void update( final User entity ){
		//
	}
	
	@Override
	public void delete( final long id ){
		//
	}
	
	@Override
	public void delete( final List< User > entities ){
		//
	}
	
	@Override
	public void deleteAll(){
		//
	}
	
}
