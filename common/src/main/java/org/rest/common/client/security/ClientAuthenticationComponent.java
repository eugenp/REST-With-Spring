package org.rest.common.client.security;

import org.springframework.stereotype.Component;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.RequestSpecification;

@Component
public class ClientAuthenticationComponent implements IClientAuthenticationComponent {

    public ClientAuthenticationComponent() {
        super();
    }

    // API

    @Override
    public final RequestSpecification givenBasicAuthenticated(final String username, final String password) {
        return RestAssured.given().auth().preemptive().basic(username, password);
    }

}
