<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Details</title>
</head>
<body>
    <h1>Product Details</h1>
    <c:if test="${product != null}">
        <h2>${product.productName}</h2>
        <p>Description: ${product.description}</p>
        <p>Price: $${product.price}</p>
        <p>Category: ${product.categoryName}</p>
        
        <h3>Product Images</h3>
        <c:forEach var="image" items="${product.productImages}">
            <img src="${image.imageUrl}" alt="Product Image">
        </c:forEach>
        
        <h3>Related Products</h3>
        <table>
            <thead>
                <tr>
                    <th>Product Name</th>
                    <th>Description</th>
                    <th>Price</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="relatedProduct" items="${relatedProducts}">
                    <tr>
                        <td><a href="product-details?productId=${relatedProduct.productId}">${relatedProduct.productName}</a></td>
                        <td>${relatedProduct.description}</td>
                        <td>$${relatedProduct.price}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${product == null}">
        <p>Product not found.</p>
    </c:if>
</body>
</html>
