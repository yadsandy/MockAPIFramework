package com.example.mockapi.model;

public class CheckoutResponse {
    private String message;

    public CheckoutResponse() {
    }

    public CheckoutResponse(String message) {
        this.message = message;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
