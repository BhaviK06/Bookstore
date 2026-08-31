package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BookController {

    @GetMapping("/index")
    public Book index() {
        return new Book(
                "Spring Boot in Action",
                "John Doe",
                2024,
                "978-1-234567-89-0",
                29.99);
    }
}
