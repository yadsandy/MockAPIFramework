package com.example.mockapi.controller;

import com.example.mockapi.model.CartRequest;
import com.example.mockapi.model.CartResponse;
import com.example.mockapi.service.CartValidator;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class CartControllerMockTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9090);
    CartValidator cartValidator = Mockito.mock(CartValidator.class);
    CartRequest request = new CartRequest();
    @Autowired
    private CartController cartController;

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:9090";
    }

    @Test
    public void testAddToCart_Success() {
        request.setItemId("1");
        request.setQuantity(1);
        String userID = "23";
        cartController = new CartController(cartValidator);
        ResponseEntity<CartResponse> responseEntity = cartController.addToCart(userID, request);
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        CartResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(responseBody.getMessage(), "Item added to cart for user: " + userID);
    }

    @Test
    public void testViewCart_Success() {
        String userID = "23";
        cartController = new CartController(cartValidator);
        ResponseEntity<CartResponse> responseEntity = cartController.viewCart(userID);
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        CartResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(responseBody.getMessage(), "Viewing cart for user: " + userID);
    }

    @Test
    public void testViewCart_Failed() {
        String userID = "2";
        cartController = new CartController(cartValidator);
        ResponseEntity<CartResponse> responseEntity = cartController.viewCart(userID);
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        CartResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(responseBody.getMessage(), "Cart not found for user: " + userID);
    }

    @Test
    public void testClearCart_Success() {
        String userID = "23";
        cartController = new CartController(cartValidator);
        ResponseEntity<CartResponse> responseEntity = cartController.clearCart(userID);
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        CartResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(responseBody.getMessage(), "Cart cleared for user: " + userID);
    }

}
