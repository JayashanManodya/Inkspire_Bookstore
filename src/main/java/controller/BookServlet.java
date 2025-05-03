package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Book;
import service.BookService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/books/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 5,   // 5 MB
    maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class BookServlet extends HttpServlet {
    private BookService bookService;
    private static final String UPLOAD_DIRECTORY = "bookphotos";

    @Override
    public void init() throws ServletException {
        bookService = new BookService(getServletContext());
        // Create upload directory if it doesn't exist
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            String action = request.getParameter("action");

            if ("search".equals(action)) {
                String query = request.getParameter("query");
                String searchType = request.getParameter("searchType");
                List<Book> books = bookService.searchBooks(query, searchType);
                request.setAttribute("books", books);
                request.setAttribute("searchQuery", query);
                request.setAttribute("searchType", searchType);
            } else {
                // Default action: show all books
                List<Book> books = bookService.getAllBooks();
                request.setAttribute("books", books);
            }

            request.getRequestDispatcher("/views/bookList.jsp").forward(request, response);
        } else {
            // Handle individual book view
            try {
                int bookId = Integer.parseInt(pathInfo.substring(1));
                Book book = bookService.getBookById(bookId);
                if (book != null) {
                    request.setAttribute("book", book);
                    request.getRequestDispatcher("/views/bookDetails.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            // Get form data
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            double price = Double.parseDouble(request.getParameter("price"));
            double rating = Double.parseDouble(request.getParameter("rating"));
            String bookType = request.getParameter("bookType");

            // Handle file upload
            Part filePart = request.getPart("photo");
            String fileName = getSubmittedFileName(filePart);
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

            // Save the file
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
            filePart.write(uploadPath + File.separator + uniqueFileName);

            // Create new book
            Book book = new Book(bookService.getNextId(), title, author, price, rating, uniqueFileName);
            bookService.addBook(book);
            response.sendRedirect(request.getContextPath() + "/views/adminDashboard.jsp");
        } else {
            // Handle book creation/update if needed
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    private String getSubmittedFileName(Part part) {
        String header = part.getHeader("content-disposition");
        for (String token : header.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}