package org.rest.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource( { "classpath*:/rest_config.xml", "classpath*:/rest_persistence.xml" } )
@ComponentScan( basePackages = "org.rest",excludeFilters = { @Filter( Configuration.class ) } )
public class WebConfig{
	
	public WebConfig(){
		super();
	}

}
