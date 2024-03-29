package com.farion.onlinebookstore.repository;

import com.farion.onlinebookstore.entity.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
