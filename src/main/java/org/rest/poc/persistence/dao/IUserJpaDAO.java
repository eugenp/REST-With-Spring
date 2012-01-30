package org.rest.poc.persistence.dao;

import org.rest.poc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserJpaDAO extends JpaRepository< User, Long >{
	
	User findByName( final String name );
	
}
