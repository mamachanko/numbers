package io.github.mamachanko.numbersclient;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@EnableDiscoveryClient
@SpringBootApplication
public class NumbersClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NumbersClientApplication.class, args);
    }
}

@RestController
class NumbersController {

    private NumbersService numbersService;

    public NumbersController(NumbersService numbersService) {
        this.numbersService = numbersService;
    }

    @GetMapping
    String get() {
        return String.format("The number is: '%d'.", numbersService.getNumber());
    }
}

@Configuration
class Config {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}

@Service
class NumbersService {

    private final RestTemplate restTemplate;

    NumbersService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Integer getNumber() {
        String auth = "user:password";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", authHeader);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        ResponseEntity<JsonNode> numbersResponse = restTemplate.exchange(
                "//numbers-service/numbers",
                HttpMethod.GET,
                requestEntity,
                JsonNode.class
        );
        return numbersResponse.getBody().get("number").asInt();
    }
}
