package com.example.mockapi.service;

import com.example.mockapi.model.BookRequest;

public class BookValidator {
    public boolean validateBookRequest(BookRequest request) {
        // Validate the book request, for example:
        return request.getQuery() != null || !request.getQuery().isEmpty() || !request.getQuery().equalsIgnoreCase("");
    }

}

