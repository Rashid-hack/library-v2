package com.example.library.repository;

import com.example.library.model.Book;
import org.jooq.CloseableDSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.Optional;

public class JooqBookRepository implements BookRepository{

    private static final Table<Record> BOOKS = DSL.table("books");
    private static final Field<Long> ID = DSL.field("id", Long.class);
    private static final Field<String> TITLE = DSL.field("title", String.class);
    private static final Field<String> AUTHOR = DSL.field("author", String.class);
    private static final Field<Double> PRICE = DSL.field("price", Double.class);

    private final String url;
    private final String user;
    private final String password;

    public JooqBookRepository(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private CloseableDSLContext open(){
        return DSL.using(url, user, password);
    }

    @Override
    public List<Book> getAllBooks() {
        try(CloseableDSLContext ctx = open()){
            return ctx.select(ID, TITLE, AUTHOR, PRICE)
                    .from(BOOKS)
                    .orderBy(ID)
                    .fetch(JooqBookRepository :: toBook);
        }
    }

    @Override
    public Optional<Book> findBookById(long id) {
        try(CloseableDSLContext ctx = open()){
            return ctx.select(ID, TITLE, AUTHOR, PRICE)
                    .from(BOOKS)
                    .where(ID.eq(id))
                    .fetchOptional(JooqBookRepository :: toBook);
        }
    }

    @Override
    public Book saveBook(Book book) {
        if (book == null){
            throw new IllegalArgumentException("Kitab bos ola bilmez");
        }
        if (book.getId() == 0){
            return insert(book);
        }
        return update(book);
    }

    private Book insert(Book book){
        try (CloseableDSLContext ctx = open()){
            Long newId = ctx.insertInto(BOOKS, TITLE, AUTHOR, PRICE)
                    .values(book.getTitle(), book.getAuthor(), book.getPrice())
                    .returningResult(ID)
                    .fetchOne(ID);
            return new Book(newId, book.getTitle(), book.getAuthor(), book.getPrice());

        }
    }

    private Book update(Book book){
        try(CloseableDSLContext ctx = open()){
            int rows = ctx.update(BOOKS)
                    .set(TITLE, book.getTitle())
                    .set(AUTHOR, book.getAuthor())
                    .set(PRICE, book.getPrice())
                    .where(ID.eq(book.getId()))
                    .execute();
            if (rows == 0){
                throw new IllegalArgumentException("Bu nomreli kitab yoxdur: " + book.getId());
            }
            return book;
        }
    }

    @Override
    public boolean deleteById(long id) {
        try(CloseableDSLContext ctx = open()){
            return ctx.deleteFrom(BOOKS).where(ID.eq(id)).execute() > 0;
        }
    }

    private static Book toBook(Record r){
        return new Book(r.get(ID), r.get(TITLE), r.get(AUTHOR),r.get(PRICE));
    }
}
