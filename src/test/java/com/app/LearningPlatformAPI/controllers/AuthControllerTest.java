package com.app.LearningPlatformAPI.controllers;

import com.app.LearningPlatformAPI.dto.LoginDto;
import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.entities.UserDb;
import com.app.LearningPlatformAPI.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testSignUpUser() throws Exception {
        UserDb user = new UserDb();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");

        Mockito.when(authService.SignUp(user)).thenReturn(new ResponseDto("User created", "Success"));

        mockMvc.perform(post("/auth/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    void testSignInUser() throws Exception {
        LoginDto login = new LoginDto();
        login.setEmail("test@example.com");
        login.setPassword("password");

        Mockito.when(authService.SignIn(login)).thenReturn(new ResponseDto("Login success", "token123"));

        mockMvc.perform(post("/auth/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk());
    }
}
