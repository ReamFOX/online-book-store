package com.farion.onlinebookstore.repository.book;

import com.farion.onlinebookstore.dto.book.BookSearchParameters;
import com.farion.onlinebookstore.entity.Book;
import com.farion.onlinebookstore.repository.SpecificationBuilder;
import com.farion.onlinebookstore.repository.SpecificationProviderManager;
import com.farion.onlinebookstore.util.ParameterNames;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> spec = Specification.where(null);
        spec = populateSpecificationForParameter(searchParameters.title(),
                ParameterNames.TITLE, spec);
        spec = populateSpecificationForParameter(searchParameters.author(),
                ParameterNames.AUTHOR, spec);
        spec = populateSpecificationForParameter(searchParameters.isbn(),
                ParameterNames.ISBN, spec);
        return spec;
    }

    private Specification<Book> populateSpecificationForParameter(String param,
                                                                  String parameterName,
                                                                  Specification<Book> spec) {
        if (!StringUtils.isEmpty(param)) {
            return spec.and(bookSpecificationProviderManager.getSpecificationProvider(parameterName)
                    .getSpecification(param));
        }
        return spec;
    }
}
