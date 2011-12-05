package org.rest.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.spring.application.ApplicationConfig;
import org.rest.spring.persistence.hibernate.PersistenceHibernateConfig;
import org.rest.spring.security.SecurityConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationConfig.class, PersistenceHibernateConfig.class, SecurityConfig.class },loader = AnnotationConfigContextLoader.class )
public final class SpringTest{
	
	@Test
	public final void whenSpringContextIsInstantiated_thenNoExceptions(){
		//
	}
	
}
