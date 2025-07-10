package com.soumyajit.LinkedInMicroservice.userService.Configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getBean(){
        return new ModelMapper();
    }
}
