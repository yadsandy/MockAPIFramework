package com.example.mockapi.controller;

import com.example.mockapi.model.CheckoutRequest;
import com.example.mockapi.model.CheckoutResponse;
import com.example.mockapi.service.CheckoutValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/checkout")
public class CheckoutController {

    private final CheckoutValidator checkoutValidator = new CheckoutValidator();

    public CheckoutController(CheckoutValidator checkoutValidator) {
    }

    @PostMapping
    public ResponseEntity<CheckoutResponse> checkout(@PathVariable String userId, @RequestBody CheckoutRequest request) {
        CheckoutValidator.StatusCode statusCode = checkoutValidator.validateCheckoutRequest(request);
        if (statusCode != CheckoutValidator.StatusCode.OK) {
            return new ResponseEntity<>(new CheckoutResponse("Invalid request"), HttpStatus.BAD_REQUEST);
        }

        // Add logic to process the checkout request
        boolean paymentProcessed = processPayment(request);
        if (!paymentProcessed) {
            return new ResponseEntity<>(new CheckoutResponse("Payment failed"), HttpStatus.BAD_REQUEST);
        }

        // More checkout logic can be added here

        return new ResponseEntity<>(new CheckoutResponse("Checkout successful for user: " + userId), HttpStatus.OK);
    }

    private boolean processPayment(CheckoutRequest request) {
        // Simulate payment processing
        // You can integrate with payment gateway APIs here
        // For demonstration, let's assume payment succeeds
        return true;
    }
}
