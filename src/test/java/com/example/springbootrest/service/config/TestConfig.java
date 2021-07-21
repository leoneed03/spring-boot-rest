package com.example.springbootrest.service.config;

import org.application.dummy.RepoDummy;
import org.application.dummy.RepoDummyImpl;
import org.application.dummy.RepoDummyStub;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//TODO: replace RepoDummy from main AppConfig with RepoDummyStub -- currently does not work
@Configuration
//@ComponentScan({"org.application.service",
//        "org.application.repository",
//        "org.application.model",
//        "org.application.controller"})
public class TestConfig {
    @Bean
    public RepoDummy repoDummy() {
        return new RepoDummyStub();
    }
}
