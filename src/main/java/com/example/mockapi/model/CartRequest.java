package com.example.mockapi.model;

public class CartRequest {
    private String itemId;
    private int quantity;

    public CartRequest() {
    }

    public CartRequest(String itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
