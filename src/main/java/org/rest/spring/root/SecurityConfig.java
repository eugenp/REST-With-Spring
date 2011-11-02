package org.rest.spring.root;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath*:/spring-security-context.xml" })
public class SecurityConfig {
	//
}
