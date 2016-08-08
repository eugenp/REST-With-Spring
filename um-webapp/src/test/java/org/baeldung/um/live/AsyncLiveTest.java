package org.baeldung.um.live;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.baeldung.um.service.AsyncService;
import org.junit.Test;
import org.springframework.http.MediaType;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class AsyncLiveTest {
    public static final String PREFIX = "http://localhost:8081/api/long/users";

    @Test
    public void whenCreateUserAsyncUsingCallable_thenCreatedWithDelay() {
        final Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body("{\"name\":\"" + randomAlphabetic(6) + "\",\"password\":\"" + randomAlphabetic(8) + "\"}").post(PREFIX + "/callable");

        assertEquals(201, response.getStatusCode());
        assertTrue(response.time() > AsyncService.DELAY);
        System.out.println(response.asString());
    }

    @Test
    public void whenCreateUserAsyncUsingDeferredResult_thenCreated() {
        final Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body("{\"name\":\"" + randomAlphabetic(6) + "\",\"password\":\"" + randomAlphabetic(8) + "\"}").post(PREFIX + "/def");
        assertEquals(201, response.getStatusCode());
        System.out.println(response.asString());
    }

    @Test
    public void whenCreateUserAsync_thenAccepted() throws InterruptedException {
        final Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body("{\"name\":\"" + randomAlphabetic(6) + "\",\"password\":\"" + randomAlphabetic(8) + "\"}").post(PREFIX + "/async");
        assertEquals(202, response.getStatusCode());
        assertTrue(response.time() < AsyncService.DELAY);
        final String loc = response.getHeader("Location");
        assertNotNull(loc);
        Thread.sleep(AsyncService.DELAY);
        final Response checkLocResponse = RestAssured.get(loc);
        assertEquals(200, checkLocResponse.getStatusCode());
        System.out.println(checkLocResponse.asString());

    }

}
