package com.example.bookstore.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BookstoreRepositoryTests {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    void bookRepositoryCreatesSearchesAndDeletesBooks() {
        Book savedBook = bookRepository.save(new Book("Repository Testing", "A. Author", 2024, "isbn-book-test", 12.5));

        assertNotNull(savedBook.getId());
        List<Book> matches = bookRepository.findByTitleContainingIgnoreCase("TESTING");
        assertEquals(1, matches.size());
        assertEquals(savedBook.getId(), matches.get(0).getId());

        bookRepository.deleteById(savedBook.getId());

        assertFalse(bookRepository.existsById(savedBook.getId()));
        assertTrue(bookRepository.findByTitleContainingIgnoreCase("TESTING").isEmpty());
    }

    @Test
    void categoryRepositoryCreatesSearchesAndDeletesCategories() {
        Category savedCategory = categoryRepository.save(new Category("Repository Category"));

        assertNotNull(savedCategory.getId());
        assertEquals(savedCategory.getId(),
                categoryRepository.findFirstByName("Repository Category").getId());

        categoryRepository.deleteById(savedCategory.getId());

        assertFalse(categoryRepository.existsById(savedCategory.getId()));
        assertNull(categoryRepository.findFirstByName("Repository Category"));
    }

    @Test
    void appUserRepositoryCreatesSearchesAndDeletesUsers() {
        AppUser savedUser = appUserRepository.save(
                new AppUser("repository-user", "encoded-password", "repository@example.test", "USER"));

        assertNotNull(savedUser.getId());
        assertEquals(savedUser.getId(), appUserRepository.findByUsername("repository-user").orElseThrow().getId());

        appUserRepository.deleteById(savedUser.getId());

        assertFalse(appUserRepository.existsById(savedUser.getId()));
        assertTrue(appUserRepository.findByUsername("repository-user").isEmpty());
    }
}
