package com.example.books;

import org.jooq.CloseableDSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.DSL;

public class BookChek {

    private static final String URL = "jdbc:postgresql://localhost:5432/library_v2";
    private static final String USER = "postgres";

    private static final Table<Record> BOOKS = DSL.table("books");
    private static final Field<Long> ID = DSL.field("id", Long.class);
    private static final Field<String> TITLE = DSL.field("title", String.class);
    private static final Field<String> AUTHOR = DSL.field("author", String.class);
    private static final Field<Double> PRICE = DSL.field("price", Double.class);

    public static void main(String[] args) {
        System.setProperty("org.jooq.no-logo", "true");
        System.setProperty("org.jooq.no-tips", "true");
        String password = System.getenv("DB_PASSWORD");
        if (password == null){
            System.out.println("DB_PASSWORD muhit deyiseni teyin olunmayib!");
            return;
        }
        try (CloseableDSLContext ctx = DSL.using(URL, USER, password)){
            int count = ctx.fetchCount(BOOKS);
            System.out.println("Kitablarin sayi: " + count);

            for (Record row : ctx.select(ID, TITLE, AUTHOR, PRICE).from(BOOKS).orderBy(ID).fetch()){
                System.out.println(row.get(ID) + " - " + row.get(TITLE) + " - " + row.get(AUTHOR) + " - " + row.get(PRICE));
            }

        }
    }
}
