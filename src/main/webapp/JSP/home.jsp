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
            <th>Product Name</th>
            <th>Price</th>
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
                <!-- Make the product name clickable -->
                <td><a href="ProductDetailsServlet?productId=<%= product.getProductId() %>"><%= product.getProductName() %></a></td>
                <td><%= product.getPrice() %></td>
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
    
    
    </br>
    </br>
    <div>
        <form action="HomeServlet" method="post">
            <label for="searchQuery">Search:</label>
            <input type="text" id="searchQuery" name="searchQuery">
            
  
        
            <select id="categoryName" name="categoryName">
             <option value="" disabled selected>Select Category</option>
                 <option value="Laptops">Laptops</option>
                <option value="TVs">TVs</option>
                <option value="Phones">Phones</option>
            </select>
            
      <label for="minPrice">Min Price:</label>
        <input type="text" id="minPrice" name="minPrice" value="0"> 

        <label for="maxPrice">Max Price:</label>
        <input type="text" id="maxPrice" name="maxPrice" value="10000">
        
        <button type="submit">Search</button>
        </form>
    </div>
    
    <h3><a href="/AddProductServlet">Click to Add Product</a></h3>
</body>
</html>

