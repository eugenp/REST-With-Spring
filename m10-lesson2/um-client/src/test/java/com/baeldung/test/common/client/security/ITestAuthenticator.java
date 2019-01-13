package com.baeldung.test.common.client.security;

import io.restassured.specification.RequestSpecification;

public interface ITestAuthenticator {

    RequestSpecification givenBasicAuthenticated(final String username, final String password);

}
