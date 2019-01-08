package io.github.mamachanko.numbersclient;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


public class NumbersServiceTest {

    @Test
    public void shouldGetNumberFromNumbersService() {
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockRestServiceServer.expect(requestTo("//numbers-service/numbers"))
                .andExpect(header("Authorization", "Basic dXNlcjpwYXNzd29yZA=="))
                .andRespond(withSuccess("{\"number\": 456}", MediaType.APPLICATION_JSON_UTF8));

        NumbersService numbersService = new NumbersService(restTemplate);
        assertThat(numbersService.getNumber()).isEqualTo(456);
        mockRestServiceServer.verify();
    }
}