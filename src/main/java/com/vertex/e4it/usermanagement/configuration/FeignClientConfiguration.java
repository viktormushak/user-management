package com.vertex.e4it.usermanagement.configuration;

import feign.Request;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

@Configuration
@EnableFeignClients(basePackages = "com.vertex.e4it.usermanagement.service")
public class FeignClientConfiguration {

    @Bean
    public static Request.Options requestOptions(ConfigurableEnvironment env) {
        int ribbonReadTimeout = env.getProperty("ribbon.ReadTimeout", int.class, 70000);
        int ribbonConnectionTimeout = env.getProperty("ribbon.ConnectTimeout", int.class, 60000);
        return new Request.Options(ribbonConnectionTimeout, ribbonReadTimeout);
    }
}
