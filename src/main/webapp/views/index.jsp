<%--
  Created by IntelliJ IDEA.
  User: Komal Dissanayaka
  Date: 4/28/2025
  Time: 5:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%
    // Redirect to IndexServlet if accessed directly
    if (request.getAttribute("books") == null) {
        response.sendRedirect(request.getContextPath() + "/index");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inkspire - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/modern.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .books-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 2rem;
            padding: 2rem 0;
        }

        .book-card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            transition: transform 0.3s ease;
            overflow: hidden;
        }

        .book-card:hover {
            transform: translateY(-5px);
        }

        .book-card img {
            width: 100%;
            height: 300px;
            object-fit: cover;
        }

        .book-card-content {
            padding: 1.5rem;
        }
    </style>
</head>
<body>

</body>
</html>
