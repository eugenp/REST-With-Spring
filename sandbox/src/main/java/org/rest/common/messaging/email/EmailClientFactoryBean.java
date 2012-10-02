package org.rest.common.messaging.email;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;

@Component
public class EmailClientFactoryBean implements FactoryBean<AmazonSimpleEmailServiceClient>, InitializingBean {

    @Autowired
    private Environment env;

    private AmazonSimpleEmailServiceClient emailClient;

    public EmailClientFactoryBean() {
        super();
    }

    // API

    @Override
    public final AmazonSimpleEmailServiceClient getObject() {
        return emailClient;
    }

    @Override
    public final Class<AmazonSimpleEmailServiceClient> getObjectType() {
        return AmazonSimpleEmailServiceClient.class;
    }

    @Override
    public final boolean isSingleton() {
        return true;
    }

    @Override
    public final void afterPropertiesSet() {
        final AWSCredentials credentials = new BasicAWSCredentials(env.getProperty("cloud.aws.accessKey"), env.getProperty("cloud.aws.secretKey"));
        emailClient = new AmazonSimpleEmailServiceClient(credentials);
    }

}
