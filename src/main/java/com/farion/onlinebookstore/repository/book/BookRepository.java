package com.farion.onlinebookstore.repository.book;

import com.farion.onlinebookstore.entity.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book>, PagingAndSortingRepository<Book, Long> {

    List<Book> findByCategories_Id(Long id);
}
