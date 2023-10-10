
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="databasemanager.DatabaseManager"%>
<%@page import="servlets.AddProductServlet"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Product</title>
</head>
<body>
    <h1>Add Product</h1>

   <!-- -->
    <form action="AddProductServlet" method="post"  >
        <label for="productName">Product Name:</label>
        <input type="text" id="productName" name="productName" required><br>

        <label for="description">Description:</label>
        <textarea id="description" name="description" required></textarea><br>

        <label for="price">Price:</label>
        <input type="number" id="price" name="price" step="0.01" required><br>

        <label for="categoryName">Category:</label>
        <select id="categoryName" name="categoryName">
    <option value="" disabled selected>Select or Add Category</option>
    <!-- Java code to retrieve and populate categories from the database -->
    <%
        List<String> categories = DatabaseManager.retrieveCategoriesFromDatabase(); // Corrected method call
        for (String category : categories) {
    %>
    <option value="<%= category %>"> <%= category %></option>
    <%
        }
    %>
</select><br>

        <label for="CategoryName">New Category:</label>
        <input type="text" id="CategoryName" name="CategoryName"><br>

        <!-- Other form fields (e.g., additional details) -->

        <button type="submit">Submit</button>
    </form>
    
    <br><br>
    
    <a href="HomeServlet">Back to Product List</a>
</body>
</html>