package com.example.mockapi.controller;

import com.example.mockapi.model.CheckoutRequest;
import com.example.mockapi.model.CheckoutResponse;
import com.example.mockapi.service.CheckoutValidator;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class CheckoutControllerMockTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9090);
    CheckoutValidator checkoutValidator = Mockito.mock(CheckoutValidator.class);
    CheckoutRequest request = new CheckoutRequest();
    @Autowired
    private CheckoutController checkoutController;

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:9090";
    }

    @Test
    public void testCheckout_Success() {
        List<String> items = new ArrayList<>();
        items.add("food");
        items.add("soap");
        items.add("water");
        request.setItems(items);
        request.setPaymentMethod("Credit Card");
        String userID = "23";
        request.setUserId(userID);
        checkoutController = new CheckoutController(checkoutValidator);
        ResponseEntity<CheckoutResponse> responseEntity = checkoutController.checkout(userID, request);
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        CheckoutResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(responseBody.getMessage(), "Checkout successful for user: 23");

    }

    @Test
    public void testCheckout_InvalidRequest() {
        List<String> items = new ArrayList<>();
        request.setItems(items);
        request.setPaymentMethod("Credit Card");
        String userID = "23";
        request.setUserId(userID);
        checkoutController = new CheckoutController(checkoutValidator);
        ResponseEntity<CheckoutResponse> responseEntity = checkoutController.checkout(userID, request);
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        CheckoutResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(responseBody.getMessage(), "Invalid request");
    }

    @Test
    public void testCheckout_InvalidPaymentMethod() {
        List<String> items = new ArrayList<>();
        items.add("food");
        items.add("soap");
        request.setItems(items);
        request.setPaymentMethod("Card");
        String userID = "23";
        request.setUserId(userID);
        checkoutController = new CheckoutController(checkoutValidator);
        ResponseEntity<CheckoutResponse> responseEntity = checkoutController.checkout(userID, request);
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        CheckoutResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(responseBody.getMessage(), "Invalid request");
    }

}
