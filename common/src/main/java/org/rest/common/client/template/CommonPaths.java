package org.rest.common.client.template;

import org.rest.common.spring.util.CommonSpringProfileUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component
@Profile(CommonSpringProfileUtil.CLIENT)
public final class CommonPaths implements InitializingBean {

    @Autowired
    private Environment env;

    private String protocol;
    private String host;
    private String port;

    public CommonPaths() {
        super();
    }

    // API

    public final String getServerRoot() {
        return protocol + "://" + host + ":" + port;
    }

    @Override
    public void afterPropertiesSet() {
        protocol = Preconditions.checkNotNull(env.getProperty("http.protocol"));
        host = Preconditions.checkNotNull(env.getProperty("http.host"));
        port = Preconditions.checkNotNull(env.getProperty("http.port"));
    }

}
