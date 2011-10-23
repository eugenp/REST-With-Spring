package org.rest.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
/** since Jackson is on the classpath, this will automatically create and register a default JSON converter */
public class WebConfig{
	
	// API
	
}
