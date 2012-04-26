package org.rest.spring.context;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource( "classpath*:contextConfig-test.xml" )
public class ContextTestConfig{
	//
}
