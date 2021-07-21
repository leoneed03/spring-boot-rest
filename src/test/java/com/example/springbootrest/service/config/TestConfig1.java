package com.example.springbootrest.service.config;

import org.application.dummy.RepoDummy;
import org.application.dummy.RepoDummyStub;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig1 {
    @Bean
    public RepoDummy repoDummy() {
        return new RepoDummyStub();
    }
}
