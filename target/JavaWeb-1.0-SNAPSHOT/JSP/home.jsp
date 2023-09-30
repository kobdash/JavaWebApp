<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>

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
            <td><%= product.getProductName() %></td>
            <td><%= product.getDescription() %></td>
            <td><%= product.getPrice() %></td>
            <td><%= product.getCategoryId() %></td>
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
</body>
</html>


