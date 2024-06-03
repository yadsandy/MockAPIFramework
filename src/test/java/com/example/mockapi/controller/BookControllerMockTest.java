package com.example.mockapi.controller;

import com.example.mockapi.model.BookRequest;
import com.example.mockapi.model.BookResponse;
import com.example.mockapi.service.BookService;
import com.example.mockapi.service.BookValidator;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class BookControllerMockTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9090);
    BookValidator bookValidator = Mockito.mock(BookValidator.class);
    BookService bookService = Mockito.mock(BookService.class);
    BookRequest request = new BookRequest();
    @Autowired
    private BookController bookController;

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:9090";
    }

    @Test
    public void testCheckout_Success() {
        request.setQuery("Book 1");
        bookController = new BookController();
        ResponseEntity<BookResponse> responseEntity = bookController.searchBooks(request);
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        BookResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(1, responseBody.getBooks().size());
    }

    @Test
    public void testSearchBooks_InvalidRequest() {
        request.setQuery("");
        bookController = new BookController();
        ResponseEntity<BookResponse> responseEntity = bookController.searchBooks(request);
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        BookResponse responseBody = responseEntity.getBody();
        System.out.println(responseBody.getBooks().size());
        Assert.assertEquals(0, responseBody.getBooks().size());
    }

    @Test
    public void testSearchBooks_NoResults() {
        request.setQuery("Bok");
        bookController = new BookController();
        ResponseEntity<BookResponse> responseEntity = bookController.searchBooks(request);
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        BookResponse responseBody = responseEntity.getBody();
        Assert.assertEquals(0, responseBody.getBooks().size());
    }

}
