package org.baeldung.test.common.client.security;

import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.RequestSpecification;

@Component
public class ClientAuthenticationComponent implements ITestAuthenticator {

    public ClientAuthenticationComponent() {
        super();
    }

    // API

    @Override
    public final RequestSpecification givenBasicAuthenticated(final String username, final String password) {
        Preconditions.checkNotNull(username);
        Preconditions.checkNotNull(password);
        return RestAssured.given().auth().preemptive().basic(username, password);
    }

}
