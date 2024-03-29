package com.farion.onlinebookstore.repository.impl;

import com.farion.onlinebookstore.entity.Book;
import com.farion.onlinebookstore.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    @Override
    public Book save(Book book) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t insert book " + book + " into DB", e);
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT b FROM Book b";
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(query, Book.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can`t get all books fro DB", e);
        }
    }
}
