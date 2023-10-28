
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="servlets.HomeServlet"%>
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
    <form action="AddProductServlet" method="post" enctype="multipart/form-data" >
        <label for="productName">Product Name:</label>
        <input type="text" id="productName" name="productName" required><br>

        <label for="description">Description:</label>
        <textarea id="description" name="description" required></textarea><br>

        <label for="price">Price:</label>
        <input type="number" id="price" name="price" step="0.01" required><br>

         <select id="categoryName" name="categoryName">
             <option value="" disabled selected>Select Category</option>
                 <option value="Laptops">Laptops</option>
                <option value="TVs">TVs</option>
                <option value="Phones">Phones</option>
            </select>
        
       <label for="image">Product Image:</label>
    <input type="file" id="image" name="image" accept="image/*"><br>

        <button type="submit">Submit</button>
    </form>
    
    <br><br>
    
    <a href="HomeServlet">Back to Product List</a>
</body>
</html>