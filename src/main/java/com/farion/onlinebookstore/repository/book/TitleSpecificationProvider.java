package com.farion.onlinebookstore.repository.book;

import com.farion.onlinebookstore.entity.Book;
import com.farion.onlinebookstore.repository.SpecificationProvider;
import com.farion.onlinebookstore.util.ParameterNames;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return ParameterNames.TITLE;
    }

    public Specification<Book> getSpecification(String param) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                root.get(ParameterNames.TITLE), "%" + param + "%");
    }
}
