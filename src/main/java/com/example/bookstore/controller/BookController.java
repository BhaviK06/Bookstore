package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.BookRepository;
import com.example.bookstore.model.Category;
import com.example.bookstore.model.CategoryRepository;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {
    @Autowired
    private BookRepository bookrepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping("/booklist")
    public String listBook(Model model) {

        List<Book> books = (List<Book>) bookrepository.findAll();
        System.out.println(books);
        model.addAttribute("books", books);
        return "booklist";

    }

    @RequestMapping(value = "/add")
    public String addStudent(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addbook";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editBook(@PathVariable("id") Long bookId, Model model) {
        Book book = bookrepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book not found: " + bookId));
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryRepository.findAll());
        return "editbook";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Book book, @RequestParam("categoryId") Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found: " + categoryId));
        book.setCategory(category);
        bookrepository.save(book);
        return "redirect:/booklist";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
public String deleteBook(@PathVariable("id") Long bookId, Model model) {
bookrepository.deleteById(bookId);
return "redirect:../booklist";
}
}
