package com.baeldung.um.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan({"com.baeldung.um.persistance.model"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource({ "classpath:persistance-${persistanceTargert:local}.properties" })
public class UmContextConfig {

	public UmContextConfig() {
		super();
	}

	// beans

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		final PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
		return pspc;
	}

}
