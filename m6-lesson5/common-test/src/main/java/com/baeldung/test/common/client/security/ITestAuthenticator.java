package com.baeldung.test.common.client.security;

import com.jayway.restassured.specification.RequestSpecification;

public interface ITestAuthenticator {

    RequestSpecification givenAuthenticated(final String username, final String password);

}
