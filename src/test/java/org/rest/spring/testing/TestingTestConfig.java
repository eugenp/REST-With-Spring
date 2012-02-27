package org.rest.spring.testing;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource( "classpath*:contextConfig-test.xml" )
@ComponentScan( { "org.rest.testing", "org.rest.sec.testing" } )
public class TestingTestConfig{
	//
}
