package com.example.mockapi.controller;

import com.example.mockapi.model.LoginRequest;
import com.example.mockapi.model.LoginResponse;
import com.example.mockapi.model.RegisterRequest;
import com.example.mockapi.model.RegisterResponse;
import com.example.mockapi.service.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserValidator validator = new UserValidator();

    public UserController(UserValidator userValidator) {
    }

    @PostMapping("/users/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request) {
        UserValidator.StatusCode statusCode = validator.isValidUser(request.getUsername(), request.getPassword());

        switch (statusCode) {
            case OK:
                LoginResponse successResponse = new LoginResponse("Login successful");
                return new ResponseEntity<>(successResponse, HttpStatus.OK); // 200 OK

            case UNAUTHORIZED:
                LoginResponse unauthorizedResponse = new LoginResponse("Unauthorized: Invalid username or password");
                return new ResponseEntity<>(unauthorizedResponse, HttpStatus.UNAUTHORIZED); // 401 Unauthorized

            case PAYMENT_REQUIRED:
                LoginResponse paymentRequiredResponse = new LoginResponse("Payment Required");
                return new ResponseEntity<>(paymentRequiredResponse, HttpStatus.PAYMENT_REQUIRED); // 402 Payment Required

            case INTERNAL_SERVER_ERROR:
                LoginResponse errorResponse = new LoginResponse("Internal Server Error: Please try again later");
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error

            default:
                LoginResponse defaultResponse = new LoginResponse("Unknown error occurred");
                return new ResponseEntity<>(defaultResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    @PostMapping("/users/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest request) {
        UserValidator.StatusCode statusCode = validator.registerUser(request.getUsername(), request.getPassword());

        switch (statusCode) {
            case OK:
                RegisterResponse successResponse = new RegisterResponse("Registration successful");
                return new ResponseEntity<>(successResponse, HttpStatus.OK); // 200 OK

            case BAD_REQUEST:
                RegisterResponse badRequestResponse = new RegisterResponse("Bad Request: Invalid input");
                return new ResponseEntity<>(badRequestResponse, HttpStatus.BAD_REQUEST); // 400 Bad Request

            case INTERNAL_SERVER_ERROR:
                RegisterResponse errorResponse = new RegisterResponse("Internal Server Error: Please try again later");
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error

            case CONFLICT:
                RegisterResponse conflictResponse = new RegisterResponse("Conflict: Username already exists");
                return new ResponseEntity<>(conflictResponse, HttpStatus.CONFLICT); // 409 Conflict

            default:
                RegisterResponse defaultResponse = new RegisterResponse("Unknown error occurred");
                return new ResponseEntity<>(defaultResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}
