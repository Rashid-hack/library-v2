package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    public record BookRequest(String title, String author, Double price) {}
    public record PriceRequest(Double price){}

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.showAllBooks();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody BookRequest request){
        return bookService.addBook(request.title(), request.author(), requirePrice(request.price()));
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<Void> updatePrice(@PathVariable long id, @RequestBody PriceRequest request){
        boolean changed = bookService.updatePrice(id, requirePrice(request.price()));
        return changed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id){
        boolean deleted = bookService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    private static double requirePrice (Double price){
        if (price == null){
            throw new IllegalArgumentException("Mebleg qeyd edilmeyib!");
        }
        return price;
    }
}
