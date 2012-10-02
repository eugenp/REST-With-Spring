package org.rest.common.messaging.email.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({ "org.rest.messaging.email" })
// @PropertySource({ "file:${path:/opt/}/aws.properties" })
@PropertySource("file:/opt/aws.properties")
public class EmailConfig {
    //
}
