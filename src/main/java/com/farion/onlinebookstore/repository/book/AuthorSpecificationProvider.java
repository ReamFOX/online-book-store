package com.farion.onlinebookstore.repository.book;

import com.farion.onlinebookstore.entity.Book;
import com.farion.onlinebookstore.repository.SpecificationProvider;
import com.farion.onlinebookstore.util.ParameterNames;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return ParameterNames.AUTHOR;
    }

    public Specification<Book> getSpecification(String param) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                root.get(ParameterNames.AUTHOR), "%" + param + "%");
    }
}
