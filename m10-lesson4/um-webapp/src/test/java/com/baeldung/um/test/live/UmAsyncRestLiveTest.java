package com.baeldung.um.test.live;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.baeldung.um.service.AsyncService;
import com.baeldung.um.util.Um;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class UmAsyncRestLiveTest {

    public static final String PREFIX = "http://localhost:8082/um-webapp/api/long/users";

    @Test
    public void whenCreateUserAsyncUsingCallable_thenCreatedWithDelay() {
        final Response response = createRandomUser().post(PREFIX + "/callable");
        System.out.println(response.asString());

        assertEquals(201, response.getStatusCode());
        assertNotNull(response.jsonPath().get("name"));
        assertTrue(response.time() > AsyncService.DELAY);
    }

    @Test
    public void whenCreateUserAsyncUsingDeferredResult_thenCreated() {
        final Response response = createRandomUser().post(PREFIX + "/deferred");
        System.out.println(response.asString());

        assertEquals(201, response.getStatusCode());
        assertNotNull(response.jsonPath().get("name"));
    }

    @Test
    public void whenCreateUserAsync_thenAccepted() throws InterruptedException {
        final Response response = createRandomUser().post(PREFIX + "/async");
        assertEquals(202, response.getStatusCode());
        assertTrue(response.time() < AsyncService.DELAY);
        final String loc = response.getHeader("Location");
        assertNotNull(loc);

        // check loc first time
        final Response checkLocResponse = givenAuth().get(loc);
        assertEquals(202, checkLocResponse.getStatusCode());
        assertFalse(checkLocResponse.asString().contains("name"));

        // check loc after delay
        Thread.sleep(2 * AsyncService.DELAY);
        final Response finalLocResponse = givenAuth().get(loc);
        assertEquals(200, finalLocResponse.getStatusCode());
        assertNotNull(finalLocResponse.jsonPath().get("name"));
    }

    //

    private RequestSpecification createRandomUser() {
        return givenAuth().contentType(MediaType.APPLICATION_JSON_VALUE).body("{\"name\":\"" + randomAlphabetic(6) + "\",\"password\":\"" + randomAlphabetic(8) + "\"}");
    }

    private RequestSpecification givenAuth() {
        return RestAssured.given().auth().basic(Um.ADMIN_EMAIL, Um.ADMIN_PASS);
    }

}
