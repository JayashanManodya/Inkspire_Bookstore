package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Book;
import model.Review;
import service.BookService;
import service.ReviewService;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"", "/index"})
public class IndexServlet extends HttpServlet {
    private BookService bookService;
    private ReviewService reviewService;

    @Override
    public void init() throws ServletException {
        bookService = new BookService(getServletContext());
        reviewService = new ReviewService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get all books
        List<Book> books = bookService.getAllBooks();
        request.setAttribute("books", books);

        // Get top 3 reviews
        List<Review> topReviews = reviewService.getTopReviews(3);
        request.setAttribute("topReviews", topReviews);

        request.getRequestDispatcher("/views/index.jsp").forward(request, response);
    }
} 