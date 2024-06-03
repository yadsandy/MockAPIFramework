package com.example.mockapi.controller;

import com.example.mockapi.model.CartRequest;
import com.example.mockapi.model.CartResponse;
import com.example.mockapi.service.CartValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users/{userId}/cart")
public class CartController {

    private final CartValidator cartValidator = new CartValidator();

    public CartController(CartValidator cartValidator) {
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(@PathVariable String userId, @RequestBody CartRequest request) {
        // Validate the request
        CartValidator.StatusCode statusCode = cartValidator.validateCartRequest(request);
        if (statusCode != CartValidator.StatusCode.OK) {
            return new ResponseEntity<>(new CartResponse("Invalid request"), HttpStatus.BAD_REQUEST);
        }

        // Add item to cart
        cartValidator.addToCart(userId, request.getItemId(), request.getQuantity());

        return new ResponseEntity<>(new CartResponse("Item added to cart for user: " + userId), HttpStatus.OK);
    }

    @GetMapping("/view")
    public ResponseEntity<CartResponse> viewCart(@PathVariable String userId) {
        // Retrieve user's cart
        Map<String, Integer> cart = cartValidator.getUserCart(userId);

        // If cart is empty, return not found
        if (cart.isEmpty()) {
            return new ResponseEntity<>(new CartResponse("Cart not found for user: " + userId), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CartResponse("Viewing cart for user: " + userId, cart), HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<CartResponse> clearCart(@PathVariable String userId) {
        // Clear user's cart
        cartValidator.clearCart(userId);

        return new ResponseEntity<>(new CartResponse("Cart cleared for user: " + userId), HttpStatus.OK);
    }
}