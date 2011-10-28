package org.rest.spring;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan( basePackages = "org.rest",excludeFilters = { @ComponentScan.Filter( Configuration.class ) } )
// @PropertySource( { "classpath:persistence.properties", "classpath:restfull.properties" } )
public class ApplicationConfig{
	
	// API
	
	@Bean
	public PropertyPlaceholderConfigurer properties(){
		final PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		final Resource[] resources = new ClassPathResource[ ] { new ClassPathResource( "persistence.properties" ), new ClassPathResource( "restfull.properties" ) };
		ppc.setLocations( resources );
		ppc.setIgnoreUnresolvablePlaceholders( true );
		return ppc;
	}
	
}
