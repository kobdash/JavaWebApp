<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Product</title>
</head>
<body>
    <h1>Add a New Product</h1>
    <form action="add-product" method="post">
        <label for="productName">Product Name:</label>
        <input type="text" id="productName" name="productName" required><br>

        <label for="description">Description:</label>
        <textarea id="description" name="description" required></textarea><br>

        <label for="price">Price:</label>
        <input type="number" id="price" name="price" step="0.01" required><br>

        <label for="categoryId">Category:</label>
        <select id="categoryId" name="categoryId" required>
            <!-- Populate the category options from the database -->
            <c:forEach var="category" items="${categories}">
                <option value="${category.categoryId}">${category.categoryName}</option>
            </c:forEach>
        </select><br>

        <!-- Add input fields for uploading multiple product images if needed -->

        <input type="submit" value="Add Product">
    </form>
</body>
</html>
