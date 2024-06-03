package com.example.mockapi.model;

import java.util.Map;

public class CartResponse {
    private String message;
    private Map<String, Integer> cartItems;

    public CartResponse() {
    }

    public CartResponse(String message) {
        this.message = message;
    }

    public CartResponse(String message, Map<String, Integer> cartItems) {
        this.message = message;
        this.cartItems = cartItems;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Integer> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<String, Integer> cartItems) {
        this.cartItems = cartItems;
    }
}