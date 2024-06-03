package com.example.mockapi.service;

import com.example.mockapi.model.CartRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CartValidator {

    private static final String SAMPLE_USERID = "23";
    private static final String ITEM_ID = "1";
    private Map<String, Map<String, Integer>> userCarts = new HashMap<>();

    public StatusCode validateCartRequest(CartRequest request) {
        if (request == null || request.getItemId() == null || request.getQuantity() <= 0) {
            return StatusCode.BAD_REQUEST; // 400 - Bad Request
        }

        // Validate item ID format (e.g., alphanumeric with dashes)
        if (!isValidItemIdFormat(request.getItemId())) {
            return StatusCode.BAD_REQUEST; // 400 - Bad Request
        }

        // Additional validation logic can be added here

        return StatusCode.OK; // 200 - OK
    }

    private boolean isValidItemIdFormat(String itemId) {
        // Regular expression for alphanumeric characters and dashes
        String regex = "^[a-zA-Z0-9-]+$";
        return Pattern.matches(regex, itemId);
    }

    public void addToCart(String userId, String itemId, int quantity) {
        // Retrieve user's cart or create a new one if it doesn't exist
        Map<String, Integer> cart = userCarts.getOrDefault(userId, new HashMap<>());

        // Update quantity if item already exists in the cart; otherwise, add it
        cart.put(itemId, cart.getOrDefault(itemId, 0) + quantity);

        // Update user's cart in the storage
        userCarts.put(userId, cart);
    }

    public Map<String, Integer> getUserCart(String userId) {
        Map<String, Integer> cart = userCarts.getOrDefault(userId, new HashMap<>());

        // Retrieve user's cart from the storage
        if (userId.equalsIgnoreCase(SAMPLE_USERID)) {
            cart.put(SAMPLE_USERID, cart.getOrDefault(ITEM_ID, 0) + 1);
        }
        return cart;
    }

    public void clearCart(String userId) {
        Map<String, Integer> cart = userCarts.getOrDefault(userId, new HashMap<>());
        if (userId.equalsIgnoreCase(SAMPLE_USERID)) {
            cart.put(SAMPLE_USERID, cart.getOrDefault(ITEM_ID, 0) + 1);
        }
        userCarts.remove(userId);
    }

    public enum StatusCode {
        OK(200),
        BAD_REQUEST(400),
        NOT_FOUND(404);

        private final int code;

        StatusCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
