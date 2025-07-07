package com.example.shopproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ShopProjectApplicationTests {

    @Configuration
    static class TestConfig {
    }
        @Test
    void contextLoads() {
    }

}
