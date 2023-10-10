<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Details</title>
</head>
<body>
    <h1>Product Details</h1>
    <%
        // Retrieve the product details and image URLs from the request attributes
        model.Product product = (model.Product) request.getAttribute("product");
        List<String> imageUrls = (List<String>) request.getAttribute("imageUrls");

        if (product != null) {
    %>
  
    <table border="1">
        <tr>
            <th>Product ID</th>
            <th>Product Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Category Name</th>
        </tr>
        <tr>
            <td><%= product.getProductId() %></td>
            <td><%= product.getProductName() %></td>
            <td><%= product.getDescription() %></td>
            <td><%= product.getPrice() %></td>
            <td><%= product.getCategoryName() %></td>
        </tr>
    </table>
    
    <h2>Product Images</h2>
    <div>
        <%
            if (imageUrls != null) {  // Check if imageUrls is not null
                for (String imageUrl : imageUrls) {
        %>
        <img src="<%= imageUrl %>" alt="Product Image" style="max-width: 300px; margin: 10px;">
        <%
                }
            }
        %>
    </div>
    <%
        } else {
    %>
    <p>Product not found.</p>
    <%
        }
    %>
</body>
</html>