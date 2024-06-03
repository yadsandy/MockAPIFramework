package com.example.mockapi.controller;

import com.example.mockapi.model.Book;
import com.example.mockapi.model.BookRequest;
import com.example.mockapi.model.BookResponse;
import com.example.mockapi.service.BookService;
import com.example.mockapi.service.BookValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService = new BookService();
    private final BookValidator bookValidator = new BookValidator();

    public BookController() {
    }

    @GetMapping("/books")
    public ResponseEntity<BookResponse> searchBooks(@RequestParam BookRequest request) {
        if (!bookValidator.validateBookRequest(request)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Book> books = bookService.searchBooks(request.getQuery());
        BookResponse response = new BookResponse();
        response.setBooks(books);
        if (books.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }


        response.setBooks(books);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}