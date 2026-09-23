package com.example.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.BookRepository;
import com.example.bookstore.model.Category;
import com.example.bookstore.model.CategoryRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
public CommandLineRunner initializedatabase(BookRepository bookRepository, CategoryRepository categoryRepository) {
	return (args) -> {
		Category fiction = getOrCreateCategory(categoryRepository, "Fiction");
		Category nonFiction = getOrCreateCategory(categoryRepository, "Non-fiction");
		Category fantasy = getOrCreateCategory(categoryRepository, "Fantasy");

		if (bookRepository.count() == 0) {
			Book book1 = new Book();
			book1.setTitle("The Odyssey");
			book1.setCategory(fiction);
			bookRepository.save(book1);

			Book book2 = new Book();
			book2.setTitle("Atomic Habits");
			book2.setCategory(nonFiction);
			bookRepository.save(book2);

			Book book3 = new Book();
			book3.setTitle("The Alchemist");
			book3.setCategory(fiction);
			bookRepository.save(book3);

			Book book4 = new Book();
			book4.setTitle("The Midnight Library");
			book4.setCategory(fiction);
			bookRepository.save(book4);

			Book book5 = new Book();
			book5.setTitle("The Psychology of Money");
			book5.setCategory(nonFiction);
			bookRepository.save(book5);

			Book book6 = new Book();
			book6.setTitle("Harry Potter and the Philosopher's Stone");
			book6.setCategory(fantasy);
			bookRepository.save(book6);
		}
	};
}

	private Category getOrCreateCategory(CategoryRepository categoryRepository, String name) {
		Category category = categoryRepository.findFirstByName(name);
		return category != null
				? category
				: categoryRepository.save(new Category(name));
	}

}
