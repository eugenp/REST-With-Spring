package org.rest.poc.persistence.dao.bar;

import org.rest.poc.model.Bar;
import org.rest.poc.model.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBarJpaDAO extends JpaRepository< Bar, Long >{
	
	Foo findByName( final String name );
	
}
