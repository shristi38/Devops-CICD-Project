package com.devops;
import org.junit.jupiter.api.Test;
public class AppTest {
    @Test
    public void testMessage() {
        org.junit.jupiter.api.Assertions.assertEquals(
            "Hello from DevOps CI/CD Pipeline!",
            App.getMessage()
        );
    }
}
