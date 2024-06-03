package com.example.mockapi.service;

import com.example.mockapi.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    // Mock data or integration with a book repository
    public List<Book> searchBooks(String query) {
        List<Book> allBooks = List.of(
                new Book("123", "Book 1", "Author 1"),
                new Book("456", "Book 2", "Author 2"),
                new Book("789", "Another Book", "Another Author")
        );

        // Filter books based on the query
        return allBooks.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        book.getAuthor().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
