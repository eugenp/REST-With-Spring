package org.rest.spring.root;

import org.rest.spring.application.ApplicationConfig;
import org.rest.spring.persistence.hibernate.PersistenceHibernateConfig;
import org.rest.spring.security.SecurityConfig;
import org.rest.spring.web.WebConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import( { PersistenceHibernateConfig.class, SecurityConfig.class, WebConfig.class, ApplicationConfig.class } )
public class SpringMainConfig{
	
	// API
	
}
