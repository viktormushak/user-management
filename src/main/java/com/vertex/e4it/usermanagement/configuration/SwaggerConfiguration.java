package com.vertex.e4it.usermanagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.vertex.e4it.usermanagement.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiMetaData());
    }

    private ApiInfo apiMetaData(){
        return new ApiInfo(
                "User Management",
                "User Management REST API for E4IT",
                "0.0.1-SNAPSHOT",
                "Terms of service",
                new Contact("Viktor_M", "https://gitlab.com/Viktor_M", "viktor.mushak@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList()
        );
    }
}
