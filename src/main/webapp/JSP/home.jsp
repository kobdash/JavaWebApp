<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
    <h1>Product List</h1>
    
   <!-- Display error message from init() if present -->
    <c:if test="${not empty errorMessage}">
        <p>Error during initialization: ${errorMessage}</p>
    </c:if>
    
    <!-- Display error message if present -->
    <c:if test="${not empty errorMessageFromRequest}">
        <p>Error from request: ${errorMessageFromRequest}</p>
    </c:if>
    <table>
        <thead>
            <tr>
                <th>Product Name</th>
                <th>Description</th>
                <th>Price</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td><a href="product-details?productId=${product.productId}">${product.productName}</a></td>
                    <td>${product.description}</td>
                    <td>${product.price}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>