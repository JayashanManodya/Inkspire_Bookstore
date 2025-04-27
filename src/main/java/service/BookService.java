package service;

import jakarta.servlet.ServletContext;
import model.Book;

import java.io.*;

public class BookService {

    private final String BOOKS_FILE;
    private final ServletContext context;

    public BookService(ServletContext context) {
        this.context = context;

        String realPath = context.getRealPath("/");
        BOOKS_FILE = realPath + "data" + File.separator + "books.txt";
        System.out.println("Books file path: " + BOOKS_FILE); // Debug log
    }
}
