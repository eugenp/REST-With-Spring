package org.rest.sec.persistence.dao;

import org.rest.sec.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleJpaDAO extends JpaRepository< Role, Long >{
	
	Role findByName( final String name );
	
}
