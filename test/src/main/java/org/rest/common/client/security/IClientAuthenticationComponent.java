package org.rest.common.client.security;

import com.jayway.restassured.specification.RequestSpecification;

public interface IClientAuthenticationComponent {

    RequestSpecification givenBasicAuthenticated(final String username, final String password);

}
