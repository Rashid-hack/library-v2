package com.example.library.repository;

import com.example.library.model.Book;

import java.util.*;

public class InMemoryBookRepository implements BookRepository {

    private long lastId;
    private final Map<Long, Book> bookMap = new LinkedHashMap<>();

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(bookMap.values());
    }

    @Override
    public Book saveBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Melumatlar tam daxil edilmeyib");
        }
        if (book.getId() == 0) {
            Book withId = new Book(++lastId, book.getTitle(), book.getAuthor(), book.getPrice());
            bookMap.put(withId.getId(), withId);
            return withId;
        }
        bookMap.put(book.getId(), book);
        return book;
    }

    @Override
    public Optional<Book> findBookById(long id) {
        return Optional.ofNullable(bookMap.get(id));
    }

    @Override
    public boolean deleteById(long id) {
        Book removed = bookMap.remove(id);
        return removed != null;
    }
}
