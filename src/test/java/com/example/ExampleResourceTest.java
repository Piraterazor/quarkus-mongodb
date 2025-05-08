package com.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * This test has been disabled as ExampleResource has been replaced by MovieResource.
 * @see com.example.resource.MovieResource
 */
@QuarkusTest
@Disabled("ExampleResource has been replaced by MovieResource")
class ExampleResourceTest {
    @Test
    void testHelloEndpoint() {
        // This test is disabled
    }
}
