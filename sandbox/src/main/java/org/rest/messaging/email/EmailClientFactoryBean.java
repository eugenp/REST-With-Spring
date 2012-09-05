package org.rest.messaging.email;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;

@Component
public class EmailClientFactoryBean implements FactoryBean<AmazonSimpleEmailServiceClient>, InitializingBean {

    @Value("cloud.aws.accessKey")
    private String accessKey;
    @Value("cloud.aws.secretKey")
    private String secretKey;

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
        final AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        emailClient = new AmazonSimpleEmailServiceClient(credentials);
    }

}
