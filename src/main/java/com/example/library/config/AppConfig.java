package com.example.library.config;

import com.example.library.service.BookService;
import com.example.library.repository.JooqBookRepository;
import com.example.library.repository.BookRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public BookRepository bookRepository(){
        String password = System.getenv("DB_PASSWORD");
        if (password == null){
            throw new IllegalArgumentException("DB_PASSWORD muhi deyiseni kimi teyin edilmeyib!");
        }
        return new JooqBookRepository(
                "jdbc:postgresql://localhost:5432/library_v2",
                "postgres",
                password);
    }

    @Bean
    public BookService bookService(BookRepository bookRepository){
        return new BookService(bookRepository);
    }
}
