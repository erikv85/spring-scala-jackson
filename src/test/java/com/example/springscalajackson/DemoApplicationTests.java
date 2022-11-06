package com.example.anotherscalajackson;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.module.scala.DefaultScalaModule$;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    @LocalServerPort
    private int port;

    @Test
    void testControllerSerialization() {
        var restTemplate = new RestTemplate();

        /* Set up the rest template's ObjectMapper to handle case classes and
         * Options correctly.
         */
        var converter = new MappingJackson2HttpMessageConverter();
        var om = new Jackson2ObjectMapperBuilder()
                .serializationInclusion(JsonInclude.Include.NON_ABSENT)
                .build();
        om.registerModule(DefaultScalaModule$.MODULE$);
        converter.setObjectMapper(om);

        restTemplate.setMessageConverters(List.of(converter));
        var resp = restTemplate.getForObject("http://localhost:" + port + "/hi", Message.class);
        System.out.println(resp);
    }
}
