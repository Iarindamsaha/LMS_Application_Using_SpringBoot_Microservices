package com.lms.user_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class AllConfigurations {


    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    SimpleMailMessage simpleMailMessage(){
        return new SimpleMailMessage();
    }


}
