package com.baeldung.test.common.client.security;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.baeldung.common.client.WebProperties;
import com.baeldung.test.common.client.security.ITestAuthenticator;
import com.baeldung.test.common.client.security.TokenResponse;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.OAuthSignature;
import com.jayway.restassured.specification.RequestSpecification;

@Component
@Profile("client")
public class OAuthAuthenticator implements ITestAuthenticator {
    private Logger log = LoggerFactory.getLogger(getClass());

    private static final String CLIENT_ID = "live-test";
    private static final String CLIENT_SECRET = "bGl2ZS10ZXN0";

    @Autowired
    private WebProperties webProps;

    public OAuthAuthenticator() {
        super();
    }

    // API

    @Override
    public final RequestSpecification givenAuthenticated(final String username, final String password) {
        final String accessToken = getAccessToken(username, password);
        return RestAssured.given().auth().oauth2(accessToken, OAuthSignature.HEADER);
    }

    final String getAccessToken(final String username, final String password) {
        try {
            final URI uri = new URI(webProps.getProtocol(), null, webProps.getHost(), webProps.getPort(), webProps.getPath() + webProps.getOauthPath(), null, null);
            final String url = uri.toString();
            final String encodedCredentials = new String(Base64.encodeBase64((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));

            final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "password");
            params.add("client_id", CLIENT_ID);
            params.add("username", username);
            params.add("password", password);

            final HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + encodedCredentials);

            final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            final RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            final TokenResponse tokenResponse = restTemplate.postForObject(url, request, TokenResponse.class);
            final String accessToken = tokenResponse.getAccessToken();
            return accessToken;
        } catch (final HttpClientErrorException e) {
            log.warn("", e);
            log.info("Full Body = {}", e.getResponseBodyAsString());
        } catch (final URISyntaxException e) {
            log.warn("", e);
        }

        return null;
    }

}
