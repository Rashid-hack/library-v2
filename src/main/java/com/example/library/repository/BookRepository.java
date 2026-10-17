package com.example.library.repository;

import com.example.library.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository{

    List<Book> getAllBooks();
    Optional<Book> findBookById(long id);
    Book saveBook(Book newBook);
    boolean deleteById(long id);
    long giveNewId();
}
