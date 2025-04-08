# Online Bookstore Management System

A web-based bookstore management system built with Java Servlet and JSP technologies. This application allows users to manage books, handle inventory, and process online orders.

## Features

- Book management (Add, Update, Delete, View)
- Inventory tracking
- User authentication and authorization
- Responsive web interface
- RESTful API endpoints

## Technology Stack

- Java 17
- Jakarta Servlet 6.0
- Jakarta Server Pages (JSP)
- JSTL 3.0
- Maven for dependency management
- Gson for JSON processing

## Prerequisites

- Java Development Kit (JDK) 17 or higher
- Maven 3.6 or higher
- A Java EE compatible web server (e.g., Apache Tomcat, GlassFish)

## Installation

1. Clone the repository:
```bash
git clone [your-repository-url]
```

2. Navigate to the project directory:
```bash
cd Online-Bookstore-Management-Sys
```

3. Build the project using Maven:
```bash
mvn clean install
```

4. Deploy the generated WAR file to your web server

## Project Structure

```
Online-Bookstore-Management-Sys/
├── src/
│   ├── main/
│   │   ├── java/                    # Java source files
│   │   │   ├── controller/          # Servlet controllers
│   │   │   │   ├── AdminServlet.java
│   │   │   │   ├── BookServlet.java
│   │   │   │   ├── CartServlet.java
│   │   │   │   ├── OrderServlet.java
│   │   │   │   ├── ReviewServlet.java
│   │   │   │   └── UserServlet.java
│   │   │   ├── model/              # Data models
│   │   │   │   ├── Book.java
│   │   │   │   ├── Cart.java
│   │   │   │   ├── EBook.java
│   │   │   │   ├── FictionBook.java
│   │   │   │   ├── NonFictionBook.java
│   │   │   │   ├── Order.java
│   │   │   │   ├── PrintedBook.java
│   │   │   │   ├── Review.java
│   │   │   │   └── User.java
│   │   │   ├── service/            # Business logic
│   │   │   │   ├── BookService.java
│   │   │   │   ├── CartService.java
│   │   │   │   ├── OrderService.java
│   │   │   │   ├── ReviewService.java
│   │   │   │   └── UserService.java
│   │   │   └── data/               # Data storage
│   │   │       ├── book.txt
│   │   │       ├── cart.txt
│   │   │       ├── orders.txt
│   │   │       ├── reviews.txt
│   │   │       └── users.txt
│   │   ├── resources/    # Configuration files
│   │   └── webapp/       # Web resources (JSP, CSS, JS)
├── pom.xml               # Maven configuration
└── README.md            # Project documentation
```

## Component Details

### Controllers (`controller/`)
- `AdminServlet.java`: Handles administrative operations
- `BookServlet.java`: Manages book-related HTTP requests
- `CartServlet.java`: Processes shopping cart operations
- `OrderServlet.java`: Handles order processing
- `ReviewServlet.java`: Manages book reviews
- `UserServlet.java`: Handles user account operations

### Models (`model/`)
- `Book.java`: Base class for all book types
- `FictionBook.java` & `NonFictionBook.java`: Book genre classifications
- `EBook.java` & `PrintedBook.java`: Book format types
- `Cart.java`: Shopping cart implementation
- `Order.java`: Order data structure
- `Review.java`: Book review system
- `User.java`: User account model

### Services (`service/`)
- `BookService.java`: Book management business logic
- `CartService.java`: Shopping cart operations
- `OrderService.java`: Order processing logic
- `ReviewService.java`: Review management
- `UserService.java`: User account management

### Data Storage (`data/`)
Text-based data storage files:
- `book.txt`: Book inventory
- `cart.txt`: Shopping cart data
- `orders.txt`: Order history
- `reviews.txt`: Book reviews
- `users.txt`: User accounts

## Configuration

The application uses standard Java EE configuration. Make sure to configure your web server appropriately and set up any necessary database connections.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Jakarta EE Community
- Maven Community
- All contributors who have helped with the project
