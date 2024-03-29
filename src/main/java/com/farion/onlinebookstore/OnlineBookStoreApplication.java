package com.farion.onlinebookstore;

import com.farion.onlinebookstore.entity.Book;
import com.farion.onlinebookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreApplication {
	@Autowired
	private BookService bookService;
	public static void main(String[] args) {
		SpringApplication.run(OnlineBookStoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			Book catsCradle = new Book();
			catsCradle.setTitle("Cat`s Cradle");
			catsCradle.setAuthor("Kurt Vonnegut");
			catsCradle.setPrice(BigDecimal.valueOf(350));
			catsCradle.setIsbn("9780575006379");
			bookService.save(catsCradle);

			Book slaughterhouse = new Book();
			slaughterhouse.setTitle("Slaughterhouse 5");
			slaughterhouse.setAuthor("Kurt Vonnegut");
			slaughterhouse.setPrice(BigDecimal.valueOf(400));
			slaughterhouse.setIsbn("9780224618014");
			bookService.save(slaughterhouse);

			bookService.findAll().forEach(System.out::println);
		};
	}

}
