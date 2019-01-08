package io.github.mamachanko.numbersservice;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NumbersControllerTest {

    @Test
    public void get() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new NumbersController()).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/numbers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.number").isNumber());
    }
}