package org.rest.sec.persistence.dao;

import org.rest.sec.model.Privilege;
import org.rest.sec.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPrivilegeJpaDAO extends JpaRepository< Privilege, Long >{
	
	User findByName( final String name );
	
}
