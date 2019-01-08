package io.github.mamachanko.numbersservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.Random;

@EnableDiscoveryClient
@SpringBootApplication
public class NumbersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NumbersServiceApplication.class, args);
    }
}

@RestController("/numbers")
class NumbersController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Map<String, Object> get() {
        return Collections.singletonMap("number", new Random().nextInt());
    }
}
