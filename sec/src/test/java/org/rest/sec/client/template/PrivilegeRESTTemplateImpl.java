package org.rest.sec.client.template;

import org.rest.common.client.template.AbstractRESTTemplate;
import org.rest.sec.client.SecBusinessPaths;
import org.rest.sec.model.Privilege;
import org.rest.sec.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.jayway.restassured.specification.RequestSpecification;

@Component
@Profile("client")
public final class PrivilegeRESTTemplateImpl extends AbstractRESTTemplate<Privilege> {

    @Autowired
    protected SecBusinessPaths paths;

    public PrivilegeRESTTemplateImpl() {
        super(Privilege.class);
    }

    // template method

    @Override
    public final String getURI() {
        return paths.getPrivilegeUri();
    }

    @Override
    public final RequestSpecification givenAuthenticated(final String username, final String password) {
        Preconditions.checkState((username == null && password == null) || (username != null && password != null));
        final String usernameToUse = (username != null) ? username : SecurityConstants.ADMIN_USERNAME;
        final String passwordToUse = (username != null) ? password : SecurityConstants.ADMIN_PASSWORD;

        return auth.givenBasicAuthenticated(usernameToUse, passwordToUse);
    }

}
