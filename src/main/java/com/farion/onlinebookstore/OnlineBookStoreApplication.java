package com.farion.onlinebookstore;

import com.farion.onlinebookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineBookStoreApplication {
	@Autowired
	private BookService bookService;
	public static void main(String[] args) {
		SpringApplication.run(OnlineBookStoreApplication.class, args);
	}
}
