package io.github.mamachanko.numbersclient;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NumbersControllerTest {

    @Test
    public void get() throws Exception {
        NumbersService numbersService = mock(NumbersService.class);
        NumbersController numbersController = new NumbersController(numbersService);
        given(numbersService.getNumber()).willReturn(123);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(numbersController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("The number is: '123'."));
    }
}
