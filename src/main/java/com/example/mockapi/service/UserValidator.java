package com.example.mockapi.service;

import com.example.mockapi.model.CartRequest;

import java.util.regex.Pattern;

public class UserValidator {

    private static final String SAMPLE_USERNAME = "john.doe@example.com";
    private static final String SAMPLE_PASSWORD = "password123";

    public StatusCode isValidUser(String username, String password) {
        if (username == null || password == null) {
            return StatusCode.INTERNAL_SERVER_ERROR; // 500 - Internal Server Error
        }

        if (!username.equals(SAMPLE_USERNAME)) {
            return StatusCode.UNAUTHORIZED; // 401 - Unauthorized
        }

        if (!password.equals(SAMPLE_PASSWORD)) {
            return StatusCode.UNAUTHORIZED; // 401 - Unauthorized
        }

        return StatusCode.OK; // 200 - OK
    }

    public StatusCode registerUser(String username, String password) {
        if (username == null || password == null || username == "" || password == "") {
            return StatusCode.BAD_REQUEST; // 400 - Bad Request
        }
        if ("existinguser".equals(username)) {
            // Custom status code for conflict scenario
            return StatusCode.CONFLICT; // Custom status code
        }

        return StatusCode.OK; // 200 - OK
    }

    public StatusCode validateCartRequest(CartRequest request) {
        if (request == null || request.getItemId() == null || request.getQuantity() <= 0) {
            return StatusCode.BAD_REQUEST; // 400 - Bad Request
        }

        // Validate item ID format (e.g., alphanumeric with dashes)
        if (!isValidItemIdFormat(request.getItemId())) {
            return StatusCode.BAD_REQUEST; // 400 - Bad Request
        }

        return StatusCode.OK; // 200 - OK
    }

    private boolean isValidItemIdFormat(String itemId) {
        // Regular expression for alphanumeric characters and dashes
        String regex = "^[a-zA-Z0-9-]+$";
        return Pattern.matches(regex, itemId);
    }

    public enum StatusCode {
        OK(200),
        UNAUTHORIZED(401),
        PAYMENT_REQUIRED(402),
        INTERNAL_SERVER_ERROR(500),
        BAD_REQUEST(400),
        CONFLICT(409);


        private final int code;

        StatusCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
