package org.rest.test.scenario;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.rest.sec.client.SecBusinessPaths;
import org.rest.sec.test.SecGeneralRESTIntegrationTest;
import org.rest.testing.security.AuthenticationUtil;
import org.rest.web.http.HTTPLinkHeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.net.HttpHeaders;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@Ignore("temp")
public class ScenarioRESTIntegrationTest extends SecGeneralRESTIntegrationTest {

    @Autowired
    private SecBusinessPaths paths;

    // tests

    // GET

    @Test
    public final void whenGetIsDoneOnRoot_then() {
        // When
        final Response getOnRootResponse = givenAuthenticated().get(paths.getRootUri());

        // Then
        final List<String> allURIsDiscoverableFromRoot = HTTPLinkHeaderUtils.extractAllURIs(getOnRootResponse.getHeader(HttpHeaders.LINK));

        assertThat(allURIsDiscoverableFromRoot, not(Matchers.<String> empty()));
    }

    // util

    protected final RequestSpecification givenAuthenticated() {
        return AuthenticationUtil.givenBasicAuthenticated();
    }

}
