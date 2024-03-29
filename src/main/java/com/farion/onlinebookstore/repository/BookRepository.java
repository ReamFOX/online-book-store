package com.farion.onlinebookstore.repository;

import com.farion.onlinebookstore.entity.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findBookById(Long id);

    List<Book> findAll();
}
