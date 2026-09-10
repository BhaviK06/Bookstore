package com.example.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
public CommandLineRunner initializedatabase(BookRepository bookRepository) {
	return (args) -> {
		Book book1 = new Book();
		book1.setTitle("The Odyssey");
		bookRepository.save(book1);

		Book book2 = new Book();
		book2.setTitle("Atomic Habits");
		bookRepository.save(book2);

		Book book3 = new Book();
		book3.setTitle("The Alchemist");
		bookRepository.save(book3);

		Book book4 = new Book();
		book4.setTitle("The Midnight Library");
		bookRepository.save(book4);

		Book book5 = new Book();
		book5.setTitle("The Psychology of Money");
		bookRepository.save(book5);

		Book book6 = new Book();
		book6.setTitle("Harry Potter and the Philosopher's Stone");
		bookRepository.save(book6);
	};
}

}
