package com.example.mockapi.service;

import com.example.mockapi.model.CheckoutRequest;

import java.util.List;

public class CheckoutValidator {

    public StatusCode validateCheckoutRequest(CheckoutRequest request) {
        if (request == null || request.getUserId() == null || request.getItems() == null || request.getItems().isEmpty() || request.getPaymentMethod() == null) {
            return StatusCode.BAD_REQUEST; // 400 - Bad Request
        }

        // Additional validation logic
        if (!isValidPaymentMethod(request.getPaymentMethod())) {
            return StatusCode.BAD_REQUEST; // Invalid payment method
        }

        // You can add more validation logic here

        return StatusCode.OK; // 200 - OK
    }

    private boolean isValidPaymentMethod(String paymentMethod) {
        // Validate payment method against a predefined list
        List<String> validPaymentMethods = List.of("Credit Card", "Debit Card", "PayPal");
        return validPaymentMethods.contains(paymentMethod);
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
