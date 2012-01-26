package org.rest.spring.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan( "org.rest.security" )
@ImportResource( { "classpath*:springSecurityConfig.xml" } )
public class SecurityConfig{
	//
}
