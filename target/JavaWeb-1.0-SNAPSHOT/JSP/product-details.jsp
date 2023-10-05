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
        // Retrieve the product details from the request attribute
        model.Product product = (model.Product) request.getAttribute("product");

        if (product != null) {
    %>
    <table border="1">
        <tr>
            <th>Product ID</th>
            <th>Product Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Category ID</th>
        </tr>
        <tr>
            <td><%= product.getProductId() %></td>
            <td><%= product.getProductName() %></td>
            <td><%= product.getDescription() %></td>
            <td><%= product.getPrice() %></td>
            <td><%= product.getCategoryId() %></td>
        </tr>
    </table>
    <%
        } else {
    %>
    <p>Product not found.</p>
    <%
        }
    %>
</body>
</html>