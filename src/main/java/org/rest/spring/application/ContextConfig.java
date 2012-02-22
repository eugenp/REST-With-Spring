package org.rest.spring.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource( "classpath*:contextConfig.xml" )
public class ContextConfig{
	
	public ContextConfig(){
		super();
	}
	
}
