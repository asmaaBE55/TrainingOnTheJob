package com.tacos.tacocloud_nosql;


import com.tacos.tacocloud_nosql.controller.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * WEB TEST FOR HomeController
 **/
@WebMvcTest(HomeController.class)
public class HomeControllerTest {
    /**
     * Injects MockMvc
     **/
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))//perform get
                .andExpect(status().isOk())//expect HTTP 200
                .andExpect(view().name("home"))//expect home view
                .andExpect(content().string(
                        containsString("Welcome to...")));// expect welcome to...
    }
}
