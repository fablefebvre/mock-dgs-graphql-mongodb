package com.fabrewer.mock.graphql.repo;

import com.fabrewer.mock.graphql.codegen.types.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepo {
    private List<Book> books = new ArrayList<>();
    private final ObjectMapper mapper;
    private final ResourceLoader resourceLoader;

    public List<Book> findAll() {
        return books;
    }

    @PostConstruct
    private void init() {
        try {
            var resource = resourceLoader.getResource("classpath:data/books.json");
            try (InputStream inputStream = resource.getInputStream()) {
                books = mapper.readValue(inputStream, new TypeReference<List<Book>>() {});
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while loading books", e);
        }
    }
}