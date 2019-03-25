package com.ioteg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public HttpMessageConverters additionalConverters() {
        return new HttpMessageConverters(new XMLHttpMessageConverter(), new CSVResultEventHttpMessageConverter());
    }
}