package com.farion.onlinebookstore.service;

import com.farion.onlinebookstore.entity.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
