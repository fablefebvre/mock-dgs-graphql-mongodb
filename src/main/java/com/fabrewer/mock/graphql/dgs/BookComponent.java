package com.fabrewer.mock.graphql.dgs;

import com.fabrewer.mock.graphql.codegen.types.Book;
import com.fabrewer.mock.graphql.repo.BookRepo;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class BookComponent {

    private final BookRepo bookRepo;

    @DgsQuery
    public List<Book> allBooks() {
        return bookRepo.findAll();
    }
}
