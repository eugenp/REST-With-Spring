package org.rest.sec.persistence.dao;

import org.rest.sec.model.Principal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPrincipalJpaDAO extends JpaRepository< Principal, Long >{
	
	Principal findByName( final String name );
	
}
