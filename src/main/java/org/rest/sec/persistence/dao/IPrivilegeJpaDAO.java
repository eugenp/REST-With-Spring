package org.rest.sec.persistence.dao;

import org.rest.sec.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPrivilegeJpaDAO extends JpaRepository< Privilege, Long >{
	
	Privilege findByName( final String name );
	
}
