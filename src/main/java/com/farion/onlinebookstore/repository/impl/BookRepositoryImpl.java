package com.farion.onlinebookstore.repository.impl;

import com.farion.onlinebookstore.entity.Book;
import com.farion.onlinebookstore.repository.BookRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository {
    @Override
    public Book save(Book book) {
        return null;
    }

    @Override
    public List<Book> findAll() {
        return null;
    }
}
