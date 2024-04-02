package com.farion.onlinebookstore.repository;

import com.farion.onlinebookstore.entity.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();
}
