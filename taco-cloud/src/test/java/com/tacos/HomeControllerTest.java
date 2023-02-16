package com.tacos;

import com.tacos.controller.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
