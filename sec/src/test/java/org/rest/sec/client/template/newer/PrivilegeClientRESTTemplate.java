package org.rest.sec.client.template.newer;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.rest.common.client.template.AbstractClientRESTTemplate;
import org.rest.sec.client.SecBusinessPaths;
import org.rest.sec.model.Privilege;
import org.rest.sec.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;

@Component
@Profile("client")
public class PrivilegeClientRESTTemplate extends AbstractClientRESTTemplate<Privilege> {

    @Autowired
    private SecBusinessPaths paths;

    @Value("${http.host}")
    private String host;
    @Value("${http.port}")
    private int port;

    public PrivilegeClientRESTTemplate() {
        super(Privilege.class);
    }

    // operations

    // template method

    @Override
    public final String getURI() {
        return paths.getPrivilegeUri();
    }

    @Override
    protected void basicAuth() {
        final HttpComponentsClientHttpRequestFactory requestFactory = (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();
        final DefaultHttpClient httpClient = (DefaultHttpClient) requestFactory.getHttpClient();
        httpClient.getCredentialsProvider().setCredentials(new AuthScope(host, port, AuthScope.ANY_REALM), new UsernamePasswordCredentials(SecurityConstants.ADMIN_USERNAME, SecurityConstants.ADMIN_PASSWORD));
    }

}
