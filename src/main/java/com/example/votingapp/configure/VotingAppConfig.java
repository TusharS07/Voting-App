package com.example.votingapp.configure;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VotingAppConfig {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
