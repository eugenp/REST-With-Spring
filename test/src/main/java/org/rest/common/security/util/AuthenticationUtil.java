package org.rest.common.security.util;

import org.apache.commons.codec.binary.Base64;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.RequestSpecification;

public final class AuthenticationUtil {

    private AuthenticationUtil() {
        throw new AssertionError();
    }

    // API

    public static RequestSpecification givenBasicAuthenticated(final String username, final String password) {
        return RestAssured.given().auth().preemptive().basic(username, password);
    }

    public static String createBasicAuthenticationAuthorizationHeader(final String username, final String password) {
        final String authorisation = username + ":" + password;
        final byte[] encodedAuthorisation = Base64.encodeBase64(authorisation.getBytes());
        final String basicAuthorizationHeader = "Basic " + new String(encodedAuthorisation);
        return basicAuthorizationHeader;
    }

}
