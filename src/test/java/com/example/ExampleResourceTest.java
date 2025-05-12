package com.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * This test has been disabled as ExampleResource has been replaced by StoreResource.
 * @see com.example.resource.StoreResource
 */
@QuarkusTest
@Disabled("ExampleResource has been replaced by StoreResource")
class ExampleResourceTest {
    @Test
    void testHelloEndpoint() {
        // This test is disabled
    }
}
