package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;

import java.util.List;
import java.util.Optional;

public class BookService {

    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo){
        this.bookRepo = bookRepo;
    }

    public List<Book> showAllBooks(){
        return bookRepo.getAllBooks();
    }

    public Book addBook(String title, String author, double price){
        checkName(title, author);
        checkPrice(price);

        Book book = new Book(bookRepo.giveNewId(), title.trim(), author.trim(), price);

        return bookRepo.saveBook(book);
    }

    public boolean updatePrice(long id, double price){
        checkPrice(price);
        checkId(id);
        Optional<Book> foundBook = bookRepo.findBookById(id);
        if (foundBook.isPresent()){
            foundBook.get().setPrice(price);
            Book book = foundBook.get();
            bookRepo.saveBook(book);
            return true;
        }
        return false;
    }

    public boolean deleteById(long id){
        checkId(id);
        return bookRepo.deleteById(id);
    }

    private void checkId(long id){
        if (id <= 0){
            throw new IllegalArgumentException("ID sifir ve ya menfi ola bilmez!");
        }
    }

    private void checkName(String title, String author){
        boolean titleEmpty = (title == null || title.isBlank());
        boolean authorEmpty = (author == null || author.isBlank());
        if (titleEmpty || authorEmpty){
            throw new IllegalArgumentException("Kitab adi ve ya muellif  bos ola bilmez!");
        }
    }


    private void checkPrice(double price){
        if (price < 0){
            throw new IllegalArgumentException("Qiymet menfi ola bilmez!");
        }
    }
}
