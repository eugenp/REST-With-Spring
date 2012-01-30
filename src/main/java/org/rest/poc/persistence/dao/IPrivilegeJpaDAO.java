package org.rest.poc.persistence.dao;

import org.rest.poc.model.Privilege;
import org.rest.poc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPrivilegeJpaDAO extends JpaRepository< Privilege, Long >{
	
	User findByName( final String name );
	
}
