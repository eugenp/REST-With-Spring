package org.rest.sec.client.template;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.common.client.template.AbstractRESTTemplate;
import org.rest.common.security.util.AuthenticationUtil;
import org.rest.sec.client.SecBusinessPaths;
import org.rest.sec.model.Privilege;
import org.rest.sec.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

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
    public final RequestSpecification givenAuthenticated() {
        return AuthenticationUtil.givenBasicAuthenticated(SecurityConstants.ADMIN_USERNAME, SecurityConstants.ADMIN_PASSWORD);
    }

    @Override
    public final Privilege createNewEntity() {
        return new Privilege(randomAlphabetic(8));
    }

    @Override
    public final void invalidate(final Privilege entity) {
        entity.setName(null);
    }

    @Override
    public void change(final Privilege resource) {
        resource.setName(randomAlphabetic(8));
    }

}
