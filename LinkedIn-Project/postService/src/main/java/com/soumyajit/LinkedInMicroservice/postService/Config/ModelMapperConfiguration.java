package com.soumyajit.LinkedInMicroservice.postService.Config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {
    @Bean

    public ModelMapper getBean(){
        return new ModelMapper();
    }
}
