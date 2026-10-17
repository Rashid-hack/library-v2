package com.example.library;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.repository.InMemoryBookRepository;
import com.example.library.service.BookService;

public class Main {
    public static void main(String[] args) {

        BookRepository bookRepository = new InMemoryBookRepository();
        BookService service = new BookService(bookRepository);

        Book book1 = service.addBook("Risali Nur", "Said Nursi", 25.5);
        Book book2 = service.addBook("Sozler Kosku", "Said Nursi", 17.8);
        System.out.println(book1);
        System.out.println(book2);

        Book book3 = service.addBook("  ali ve nino  ", "  camal qasim  ", 7.5);
        System.out.println(book3);

        try {
            service.addBook("", "Said Nursi", 12.7);
            System.out.println("Bu cap olunmayacaq");
        } catch (IllegalArgumentException e){
            System.out.println("Xeta: " + e.getMessage());
        }

        try {
            service.updatePrice(1, -25.3);
            System.out.println("Cixmamalidir yazi");
        } catch (IllegalArgumentException e){
            System.out.println("Xeta: " + e.getMessage());
        }

        try {
            service.updatePrice(99, 25.45);
            System.out.println(service.updatePrice(99, 25.45));
        } catch (IllegalArgumentException e){
            System.out.println("Xeta: " + e.getMessage());
        }

        try {
            service.addBook("Sair", "Vahid", -12.51);
            System.out.println("Cixmamalidir");
        } catch (IllegalArgumentException e){
            System.out.println("Xeta: " + e.getMessage());
        }

        System.out.println(service.updatePrice(2, 5.7));

        System.out.println(service.deleteById(1));
        System.out.println(service.deleteById(1));

        for (Book book : service.showAllBooks()){
            System.out.println(book);
        }

        System.out.println("Say: " + service.showAllBooks().size());

    }
}
