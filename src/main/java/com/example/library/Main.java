package com.example.library;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.repository.InMemoryBookRepository;
import com.example.library.service.BookService;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        BookRepository bookRepository = new InMemoryBookRepository();
        BookService service = new BookService(bookRepository);

        boolean running = true;

        while (running){
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice){
                case "1" -> showAllBooks(service);
                case "2" -> addBook(service);
                case "3" -> updatePrice(service);
                case "4" -> deleteBook(service);
                case "0" -> {
                    System.out.println("Kitabxanadan cixilir sagolun.....");
                    running = false;
                }
                default -> System.out.println("Yanlis secim, zehmet olmasa 0-dan 4-e qeder bir reqem secin");
            }
        }

    }

    private static void printMenu(){
        System.out.println();
        System.out.println("======= KITABXANA MENYUSU =======");
        System.out.println("1. Kitablari goster");
        System.out.println("2. Kitab elave et");
        System.out.println("3. Kitabin qiymetini yenile");
        System.out.println("4. Kitabi sil");
        System.out.println("0. Cixis");
        System.out.print("Secim: ");
    }

    private static void showAllBooks(BookService service){
        List<Book> books = service.showAllBooks();
        if (books.isEmpty()){
            System.out.println("Ref bosdur!");
            return;
        }
        for (Book book : books){
            System.out.println(book);
        }
    }

    private static void addBook(BookService service){
        System.out.print("Kitabin adini daxil edin: ");
        String title = scanner.nextLine();
        System.out.print("Muellifin adini daxil edin: ");
        String author = scanner.nextLine();
        double price = readDouble("Qiymeti: ");
        try {
            Book saved = service.addBook(title, author, price);
            System.out.println("Elave olundu: " + saved);
        } catch (IllegalArgumentException e){
            System.out.println("Xeta: " + e.getMessage());
        }
    }

    private static double readDouble(String message){
        while (true){
            System.out.print(message);
            String text = scanner.nextLine().trim();
            try {
                return Double.parseDouble(text);
            } catch (IllegalArgumentException e){
                System.out.println("Xeta: yanliz reqemlerden istifade edin");
            }
        }
    }

    private static void updatePrice(BookService service){
        long id = readLong("Qiymetini deyismek istediyiniz kitabin Id nomresini daxil edin: ");
        double price = readDouble("Yeni qiymet: ");
        try {
            if (service.updatePrice(id, price)){
                System.out.println("Qiymet yenilendi: " + price);
            } else {
                System.out.println("Kitab tapilmadi!");
            }
        } catch (IllegalArgumentException e){
            System.out.println("Xeta: " + e.getMessage());
        }

    }

    private static long readLong(String message){
        while (true){
            System.out.print(message);
            String text = scanner.nextLine().trim();
            try {
                return Long.parseLong(text);
            } catch (IllegalArgumentException e){
                System.out.println("Xeta: yanliz reqemlerden istifade edin");
            }
        }
    }

    private static void deleteBook(BookService service){
        long id = readLong("Silmek istediyiniz kitabin Id nomresini daxil edin: ");
        try {
            if (service.deleteById(id)){
                System.out.println("Secdiyiniz kitab silindi");
            } else {
                System.out.println("Kitab tapilmadi!");
            }
        } catch (IllegalArgumentException e){
            System.out.println("Xeta: " + e.getMessage());
        }

    }
}
