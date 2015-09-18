package org.baeldung.test.common.client.security;

import com.jayway.restassured.specification.RequestSpecification;

public interface ITestAuthenticator {

    RequestSpecification givenBasicAuthenticated(final String username, final String password);

}
