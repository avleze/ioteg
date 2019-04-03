package com.ioteg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;

import com.ioteg.http.converters.CSVResultEventHttpMessageConverter;
import com.ioteg.http.converters.XMLHttpMessageConverter;


/**
 * <p>Application class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
@SpringBootApplication
public class Application {

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    /**
     * <p>additionalConverters.</p>
     *
     * @return a {@link org.springframework.boot.autoconfigure.http.HttpMessageConverters} object.
     */
    @Bean
    public HttpMessageConverters additionalConverters() {
        return new HttpMessageConverters(new XMLHttpMessageConverter(), new CSVResultEventHttpMessageConverter());
    }
}
