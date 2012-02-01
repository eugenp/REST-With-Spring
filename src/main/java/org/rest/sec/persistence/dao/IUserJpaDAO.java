package org.rest.sec.persistence.dao;

import org.rest.sec.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserJpaDAO extends JpaRepository< User, Long >{
	
	User findByName( final String name );
	
}
