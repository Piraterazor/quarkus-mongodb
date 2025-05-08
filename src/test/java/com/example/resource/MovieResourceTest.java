package com.example.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * Basic test for MovieResource.
 * Note: This test is disabled by default as it requires a MongoDB connection.
 * Enable it after configuring a test MongoDB instance.
 */
@QuarkusTest
@Disabled("Requires MongoDB connection")
public class MovieResourceTest {

    @Test
    public void testMoviesEndpoint() {
        // Test that the endpoint is available
        given()
            .when().get("/api/movies")
            .then()
            .statusCode(200);
    }
}