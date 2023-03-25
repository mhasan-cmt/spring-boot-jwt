package dev.shafin.jwtdemo.controller;

import dev.shafin.jwtdemo.security.SecurityConfig;
import dev.shafin.jwtdemo.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({HomeController.class, AuthController.class})
@Import({SecurityConfig.class, TokenService.class})
class HomeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void rootWhenUnauthenticatedThen401() throws Exception {
    this.mockMvc.perform(get("/"))
            .andExpect(status().isUnauthorized());
    }
    @Test
    void rootWhenAuthenticatedThenSayHelloUser() throws Exception{
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/token")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();
        this.mockMvc.perform(get("/")
                        .header("Authorization", "Bearer "+token))
                .andExpect(content().string("Welcome to JWT Demo user"));
    }

    @Test
    @WithMockUser
    void rootWithMockUser() throws Exception{
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}