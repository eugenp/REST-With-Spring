package com.baeldung.um.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.baeldung.um.persistence.model.Patient;

public class PatientRecordLiveTest {

    private static final String API_URI = "http://localhost:8081/um-webapp/api/patients";

    // GET

    @Test
    public void whenGetAllPatients_thenOK() {
        final Response response = RestAssured.given().auth().preemptive().basic("user@fake.com", "userpass").get(API_URI);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetPatientByName_thenOK() {
        final Response response = RestAssured.given().auth().preemptive().basic("user@fake.com", "userpass").get(API_URI + "/search?name=john");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        final XmlPath xmlPath = new XmlPath(response.asString()).setRoot("patient");
        assertEquals("john", xmlPath.get("name"));
    }

    @Test
    public void givenDoctorRole_whenUpdateCreatedPatient_thenUpdated() {
        final Patient patient = new Patient();
        patient.setId(16L);
        // update
        patient.setName("newName");
        Response response = RestAssured.given().auth().preemptive().basic("admin@fake.com", "adminpass").and().contentType(MediaType.APPLICATION_JSON_VALUE).body(patient).put(API_URI + "/16");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        // check if changes saved
        response = RestAssured.given().auth().preemptive().basic("admin@fake.com", "adminpass").get(API_URI + "/16");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.asString().contains("newName"));

    }

    @Test
    public void givenUserDoctor_whenUpdateCreatedPatient_thenUpdated() {
        final Patient patient = new Patient();
        patient.setId(16L);
        // update
        patient.setName("newName");
        Response response = RestAssured.given().auth().preemptive().basic("admin@fake.com", "adminpass").and().contentType(MediaType.APPLICATION_JSON_VALUE).body(patient).put(API_URI + "/16");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        // check if changes saved
        response = RestAssured.given().auth().preemptive().basic("admin@fake.com", "adminpass").get(API_URI + "/16");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.asString().contains("newName"));

    }

    @Test
    public void givenUserNurse_whenUpdateCreatedPatient_thenForbidden() {
        final Patient patient = new Patient();
        patient.setId(16L);
        // update
        patient.setName("sampleName");
        final Response response = RestAssured.given().auth().preemptive().basic("user@fake.com", "userpass").and().contentType(MediaType.APPLICATION_JSON_VALUE).body(patient).put(API_URI + "/16");
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode());
    }

}
