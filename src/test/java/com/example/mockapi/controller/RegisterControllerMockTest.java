package com.example.mockapi.controller;

import com.example.mockapi.model.RegisterRequest;
import com.example.mockapi.model.RegisterResponse;
import com.example.mockapi.service.UserValidator;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class RegisterControllerMockTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9090);
    UserValidator userValidator = Mockito.mock(UserValidator.class);
    RegisterRequest request = new RegisterRequest();
    @Autowired
    private UserController UserController;

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:9090";
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        request.setUsername("newuser");
        request.setPassword("newpassword123");
        // Perform the login request
        UserController = new UserController(userValidator);
        ResponseEntity<RegisterResponse> responseEntity = UserController.registerUser(request);
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        RegisterResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(responseBody.getMessage(), "Registration successful");
    }

    @Test
    public void testRegisterUser_BadRequest() throws Exception {
        request.setUsername("");
        request.setPassword("newpassword123");
        // Perform the login request
        UserController = new UserController(userValidator);
        ResponseEntity<RegisterResponse> responseEntity = UserController.registerUser(request);
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        RegisterResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(responseBody.getMessage(), "Bad Request: Invalid input");
    }

    @Test
    public void testRegisterUser_InternalServerError() throws Exception {
        request.setUsername("newuser");
        request.setPassword("");
        // Perform the login request
        UserController = new UserController(userValidator);
        ResponseEntity<RegisterResponse> responseEntity = UserController.registerUser(request);
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        RegisterResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(responseBody.getMessage(), "Bad Request: Invalid input");
    }

    @Test
    public void testRegisterUser_Conflict() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existinguser");
        request.setPassword("newpassword123");
        UserController = new UserController(userValidator);
        ResponseEntity<RegisterResponse> responseEntity = UserController.registerUser(request);
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        RegisterResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(responseBody.getMessage(), "Conflict: Username already exists");
    }
}
