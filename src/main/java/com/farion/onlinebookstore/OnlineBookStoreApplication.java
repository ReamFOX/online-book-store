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
}
