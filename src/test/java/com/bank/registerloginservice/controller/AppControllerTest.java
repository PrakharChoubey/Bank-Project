package com.bank.registerloginservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bank.registerloginservice.dto.LoginUserRequest;
import com.bank.registerloginservice.dto.RegisterRequestDto;
import com.bank.registerloginservice.dto.UserDetails;
import com.bank.registerloginservice.exceptions.InvalidTokenException;
import com.bank.registerloginservice.exceptions.UserNotFoundException;
import com.bank.registerloginservice.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringJUnitConfig
@WebMvcTest(AppController.class)
class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService service;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private AppController appController;

    @Test
    public void testRegister() throws Exception {
        RegisterRequestDto requestData = new RegisterRequestDto();
        requestData.setUsername("john.doe");
        requestData.setPassword("secret");

        UserDetails expectedResponse = new UserDetails();
        expectedResponse.setId(1);
        expectedResponse.setUsername("john.doe");

        when(service.register(any(RegisterRequestDto.class))).thenReturn(expectedResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestData)))
                .andReturn();

        UserDetails actualResponse = objectMapper.readValue(result.getResponse().getContentAsString(), UserDetails.class);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testLogin() throws Exception {
        LoginUserRequest requestData = new LoginUserRequest();
        requestData.setUsername("john.doe");
        requestData.setPassword("secret");

        String expectedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZSIsImlhdCI6MTYxODI2NjEwMSwiZXhwIjoxNjE4MjY5NzAxfQ.hkYrtCQdFxVZ0nIwEXlRLmgyKj2pgJxSx52Hggm9X9I";

        when(service.login(any(LoginUserRequest.class))).thenReturn(expectedToken);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestData)))
                .andReturn();

        String actualToken = result.getResponse().getContentAsString();

        assertEquals(expectedToken, actualToken);
    }

    @Test
    public void testCheckToken() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZSIsImlhdCI6MTYxODI2NjEwMSwiZXhwIjoxNjE4MjY5NzAxfQ.hkYrtCQdFxVZ0nIwEXlRLmgyKj2pgJxSx52Hggm9X9I";

        UserDetails expectedResponse = new UserDetails();
        expectedResponse.setId(1);
        expectedResponse.setUsername("john.doe");

        when(service.authenticateByToken(token)).thenReturn(expectedResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/checkByToken/" + token))
                .andReturn();

        UserDetails actualResponse = objectMapper.readValue(result.getResponse().getContentAsString(), UserDetails.class);

        assertEquals(expectedResponse, actualResponse);
    }
}