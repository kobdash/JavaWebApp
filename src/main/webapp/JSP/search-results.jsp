<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Results</title>
</head>
<body>
    <h1>Search Results</h1>
    <p>Results for: <strong>${query}</strong></p>
    <c:if test="${not empty searchResults}">
        <table>
            <thead>
                <tr>
                    <th>Product Name</th>
                    <th>Description</th>
                    <th>Price</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${searchResults}">
                    <tr>
                        <td><a href="product-details?productId=${product.productId}">${product.productName}</a></td>
                        <td>${product.description}</td>
                        <td>$${product.price}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty searchResults}">
        <p>No results found for the search query.</p>
    </c:if>
</body>
</html>