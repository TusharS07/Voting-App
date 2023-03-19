package com.example.votingapp.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("Voting App").apiInfo(apiInfo()).select().build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Voting App", "Voting App Using SpringBoot.", "2.0",
                "Terms of Service", new Contact("Yogesh More" ,
                "https://github.com/iyogeshmore", "iyogesh.more1998@gmail.com"),
                "Apache License Version 2.0", "https://www.apache.org/licenses/", Collections.emptyList());
    }

}
