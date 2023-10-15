<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page import="servlets.HomeServlet" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
</head>
<body>
    <h1>Product List</h1>
    <table border="1">
        <tr>
            <th>Product ID</th>
            <th>Product Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Category ID</th>
            <th>Category Name</th>
        </tr>
        <% 
            List<Product> products = (List<Product>) request.getAttribute("products");
            int itemsPerPage = 5;
            int currentPage = 1;
            // Get the current page parameter from the request
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    currentPage = Integer.parseInt(pageParam);
                } catch (NumberFormatException e) {
                    // Handle invalid page parameter
                }
            }
            // Calculate the start and end index for pagination
            int startIndex = (currentPage - 1) * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, products.size());
            // Loop through products for the current page
            for (int i = startIndex; i < endIndex; i++) {
                Product product = products.get(i);
        %>
           <tr>
                <td><%= product.getProductId() %></td>
                <!-- Make the product name clickable -->
                <td><a href="ProductDetailsServlet?productId=<%= product.getProductId() %>"><%= product.getProductName() %></a></td>
                <td><%= product.getDescription() %></td>
                <td><%= product.getPrice() %></td>
                <td><%= product.getCategoryId() %></td>
                <td><%= product.getCategoryName() %></td>
            </tr>
        <% } %>
    </table>
    <div class="pagination">
        <%
        int totalPages = (int) Math.ceil((double) products.size() / (double) itemsPerPage);
        for (int pageNumber = 1; pageNumber <= totalPages; pageNumber++) {
            String activeClass = (pageNumber == currentPage) ? "active" : "";
        %>
        <a class="<%= activeClass %>" href="HomeServlet?page=<%= pageNumber %>"><%= pageNumber %></a>
        <%
        }
        %>
    </div>
    
    <div>
        <form action="HomeServlet" method="post">
            <label for="searchQuery">Search:</label>
            <input type="text" id="searchQuery" name="searchQuery">
            <label for="categoryId">Category:</label>
            <label for="categoryName">Category:</label>
            <!-- Add a dropdown for selecting a category if needed -->
            <select id="categoryId" name="categoryId">
                <!-- Populate the dropdown options with categories if necessary -->
                 <option value="0">Select Category</option>
                <option value="1">Category 1</option>
                <option value="2">Category 2</option>
            <select id="categoryName" name="categoryName">

                  <option value="" disabled selected>Select Category</option>
                 <!-- Java code to retrieve and populate categories from the database -->
            <% 
                List<String> categories = databasemanager.DatabaseManager.retrieveCategoriesFromDatabase();// Implement this method
                for (String category : categories) {
            %>
            <option value="<%= category %>"><%= category %></option>
            <%
                }
            %>
                <!-- Add more options as needed -->
            </select>
            <label for="minPrice">Min Price:</label>
            <input type="text" id="minPrice" name="minPrice">
            <label for="maxPrice">Max Price:</label>
            <input type="text" id="maxPrice" name="maxPrice">
            <button type="submit">Search</button>
        </form>
    </div>
</body>
</html>

