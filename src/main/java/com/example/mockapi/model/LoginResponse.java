package com.example.mockapi.model;

public class LoginResponse {

    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    // Getter and setter for the token
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
