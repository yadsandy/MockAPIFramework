package com.example.mockapi.controller;

import com.example.mockapi.model.LoginRequest;
import com.example.mockapi.model.LoginResponse;
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

public class LoginControllerMockTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9090);
    UserValidator userValidator = Mockito.mock(UserValidator.class);
    LoginRequest request = new LoginRequest();
    @Autowired
    private UserController UserController;

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:9090";
    }

    @Test
    public void testLogin_Success() {
        // Create a sample LoginRequest with valid credentials
        request.setUsername("john.doe@example.com");
        request.setPassword("password123");

        // Perform the login request
        UserController = new UserController(userValidator);
        ResponseEntity<LoginResponse> responseEntity = UserController.loginUser(request);

        // Verify that the response status code is 200 (OK)
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        // Verify that the response body contains a valid token
        LoginResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(responseBody.getToken(), "Login successful");
    }

    @Test
    public void testLogin_WrongCredentials() {
        // Create a sample LoginRequest with valid credentials
        request.setUsername("john1.doe@example.com");
        request.setPassword("password123");

        // Perform the login request
        UserController = new UserController(userValidator);
        ResponseEntity<LoginResponse> responseEntity = UserController.loginUser(request);

        // Verify that the response status code is 4xx
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());

        LoginResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(responseBody.getToken(), "Unauthorized: Invalid username or password");
    }

    @Test
    public void testLogin_noEmail() {
        // Create a sample LoginRequest with valid credentials
        request.setUsername("");
        request.setPassword("password123");

        // Perform the login request
        UserController = new UserController(userValidator);
        ResponseEntity<LoginResponse> responseEntity = UserController.loginUser(request);

        // Verify that the response status code is 4xx
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());

        LoginResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(responseBody.getToken(), "Unauthorized: Invalid username or password");
    }

    @Test
    public void testLogin_noPassword() {
        // Create a sample LoginRequest with valid credentials
        request.setUsername("john1.doe@example.com");
        request.setPassword("");

        // Perform the login request
        UserController = new UserController(userValidator);
        ResponseEntity<LoginResponse> responseEntity = UserController.loginUser(request);

        // Verify that the response status code is 4xx
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());

        LoginResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(responseBody.getToken(), "Unauthorized: Invalid username or password");
    }

    @Test
    public void testLogin_noData() {
        // Create a sample LoginRequest with valid credentials
        request.setUsername("");
        request.setPassword("");

        // Perform the login request
        UserController = new UserController(userValidator);
        ResponseEntity<LoginResponse> responseEntity = UserController.loginUser(request);

        // Verify that the response status code is 4xx
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());

        LoginResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(responseBody.getToken(), "Unauthorized: Invalid username or password");
    }
}
