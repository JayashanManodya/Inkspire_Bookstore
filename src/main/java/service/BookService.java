package service;

import data_structures.BookLinkedList;
import data_structures.BookQuickSort;
import jakarta.servlet.ServletContext;
import model.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookService {
    // Using our custom LinkedList implementation for book storage
    private BookLinkedList books;
    private final String BOOKS_FILE;
    private final ServletContext context;

    public BookService(ServletContext context) {
        this.context = context;
        books = new BookLinkedList();
        // Get the real path of the deployed application
        String realPath = context.getRealPath("/");
        BOOKS_FILE = realPath + "data" + File.separator + "books.txt";
        System.out.println("Books file path: " + BOOKS_FILE); // Debug log
        loadBooks();
    }
    
    public List<Book> getAllBooks() {
        Book[] bookArray = books.getAllBooks();
        List<Book> bookList = new ArrayList<>();
        for (Book book : bookArray) {
            bookList.add(book);
        }
        return bookList;
    }
    
    public Book getBookById(int id) {
        return books.getById(id);
    }
    
    public List<Book> searchBooks(String query, String searchType) {
        if (query == null || query.trim().isEmpty()) {
            return getAllBooks();
        }
        
        String searchQuery = query.toLowerCase().trim();
        return getAllBooks().stream()
                .filter(book -> {
                    if ("title".equals(searchType)) {
                        return book.getTitle().toLowerCase().contains(searchQuery);
                    } else if ("author".equals(searchType)) {
                        return book.getAuthor().toLowerCase().contains(searchQuery);
                    } else {
                        // "all" or any other value - search in both title and author
                        return book.getTitle().toLowerCase().contains(searchQuery) || 
                               book.getAuthor().toLowerCase().contains(searchQuery);
                    }
                })
                .collect(Collectors.toList());
    }
    
    public void addBook(Book book) {
        books.add(book);
        saveBooks();
    }
    
    public void updateBook(Book updatedBook) {
        books.update(updatedBook);
        saveBooks();
    }
    
    public void removeBook(int id) {
        books.remove(id);
        saveBooks();
    }
    
    public int getNextId() {
        int maxId = 0;
        for (Book book : books) {
            if (book.getId() > maxId) {
                maxId = book.getId();
            }
        }
        return maxId + 1;
    }
    
    public List<Book> sortBooksByPrice(boolean ascending) {
        List<Book> bookList = getAllBooks();
        BookQuickSort.sortByPrice(bookList, ascending);
        return bookList;
    }
    
    public List<Book> sortBooksByRating(boolean ascending) {
        List<Book> bookList = getAllBooks();
        BookQuickSort.sortByRating(bookList, ascending);
        return bookList;
    }
    
    private void loadBooks() {
        File file = new File(BOOKS_FILE);
        try {
            // Ensure the directory exists
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                if (!parentDir.mkdirs()) {
                    throw new IOException("Failed to create directory: " + parentDir.getAbsolutePath());
                }
            }
            
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split("\\|");
                        if (parts.length >= 9) {  // Updated to check for all required fields
                            Book book = new Book(
                                Integer.parseInt(parts[0]),  // id
                                parts[1],                    // title
                                parts[2],                    // author
                                Double.parseDouble(parts[3]), // price
                                parts[4],                    // isbn
                                parts[5],                    // description
                                parts[6],                    // category
                                parts[7],                    // photo
                                Double.parseDouble(parts[8]) // rating
                            );
                            books.add(book);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading books: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void saveBooks() {
        File file = new File(BOOKS_FILE);
        try {
            // Ensure the directory exists
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                if (!parentDir.mkdirs()) {
                    throw new IOException("Failed to create directory: " + parentDir.getAbsolutePath());
                }
            }
            
            // Try to create the file if it doesn't exist
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new IOException("Failed to create file: " + file.getAbsolutePath());
                }
            }
            
            // Write to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Book book : books) {
                    writer.write(String.format("%d|%s|%s|%.2f|%s|%s|%s|%s|%.1f%n",
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getPrice(),
                        book.getIsbn(),
                        book.getDescription(),
                        book.getCategory(),
                        book.getPhoto(),
                        book.getRating()
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save books. Please try again later.", e);
        }
    }
}