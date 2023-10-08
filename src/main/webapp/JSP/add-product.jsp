<%@page import="java.util.List"%>
<%@page import="databasemanager.DatabaseManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Product</title>
</head>
<body>
    <h1>Add Product</h1>

    <form action="AddProductServlet" method="post" enctype="multipart/form-data">
        <label for="productName">Product Name:</label>
        <input type="text" id="productName" name="productName" required><br>

        <label for="description">Description:</label>
        <textarea id="description" name="description" required></textarea><br>

        <label for="price">Price:</label>
        <input type="number" id="price" name="price" step="0.01" required><br>

        <label for="images">Images:</label>
        <input type="file" id="images" name="images" accept="image/*" multiple required><br>

        <label for="category">Category:</label>
        <select id="category" name="category">
            <option value="" disabled selected>Select or Add Category</option>
            <!-- Java code to retrieve and populate categories from the database -->
            <% 
                List<String> categories = databasemanager.DatabaseManager.retrieveCategoriesFromDatabase();// Implement this method
                for (String category : categories) {
            %>
            <option value="<%= category %>"><%= category %></option>
            <%
                }
            %>
        </select><br>

        <label for="newCategory">New Category:</label>
        <input type="text" id="newCategory" name="newCategory"><br>

        <!-- Other form fields (e.g., additional details) -->

        <button type="submit">Submit</button>
    </form>
    
    <br><br>
    
    <a href="HomeServlet">Back to Product List</a>
</body>
</html>