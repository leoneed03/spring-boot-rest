package org.application.config;

import org.application.dummy.RepoDummy;
import org.application.dummy.RepoDummyImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.application")
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper;
    }

//    @Bean
//    public RepoDummy repoDummy() {
//        return new RepoDummyImpl();
//    }
}
