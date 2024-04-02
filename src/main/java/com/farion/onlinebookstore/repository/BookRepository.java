package com.farion.onlinebookstore.repository;

import com.farion.onlinebookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
