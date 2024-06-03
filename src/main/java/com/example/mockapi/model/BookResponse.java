package com.example.mockapi.model;

import java.util.List;

public class BookResponse {
    private List<Book> books;

    public BookResponse() {
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
