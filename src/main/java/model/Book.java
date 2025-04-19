package model;

public class Book {

    private int id;
    private String title;
    private String author;
    private double price;
    private double rating;
    private String photo;
    private String description;
    private String isbn;

    public Book() {
    }

    public Book(int id, String title, String author, double price, double rating, String photo, int stockQuantity, String description, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.rating = rating;
        this.photo = photo;
        this.description = description;
        this.isbn = isbn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                ", photo='" + photo + '\'' +
                ", description='" + description + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}

