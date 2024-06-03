package com.example.mockapi.model;

import java.util.List;

public class CheckoutRequest {
    // Add fields as needed for checkout request

    private String userId;
    private List<String> items;
    private String paymentMethod;

    public CheckoutRequest() {
    }

    public CheckoutRequest(String userId, List<String> items, String paymentMethod) {
        this.userId = userId;
        this.items = items;
        this.paymentMethod = paymentMethod;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
