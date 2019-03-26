package com.ioteg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;

import com.ioteg.messageconverters.CSVResultEventHttpMessageConverter;
import com.ioteg.messageconverters.XMLHttpMessageConverter;


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