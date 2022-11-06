package com.example.anotherscalajackson;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.module.scala.DefaultScalaModule$;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import scala.Option;

@SpringBootApplication
@RestController
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping(value = "/hi")
    Message hi() {
        return new Message("hi", Option.apply(null));
    }
}

@EnableWebMvc
@Configuration
class MyConfig extends WebMvcConfigurerAdapter {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        var converter = new MappingJackson2HttpMessageConverter();
        var om = new Jackson2ObjectMapperBuilder()
                .serializationInclusion(JsonInclude.Include.NON_ABSENT) /* to prevent serialization of 'None' */
                .build();
        om.registerModule(DefaultScalaModule$.MODULE$);
        converter.setObjectMapper(om);
        converters.add(converter);
    }
}
