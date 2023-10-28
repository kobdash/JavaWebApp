<%@page import="java.util.List"%>
<%@page import="java.sql.Blob"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Details</title>
</head>
<body>
   
    <%
        // Retrieve the product details and image data (LONGBLOB) from the request attributes
        model.Product product = (model.Product) request.getAttribute("product");
        Blob imageBlob = (Blob) request.getAttribute("imageBlob");

        if (product != null && imageBlob != null) {
    %>
  

    <div>
           
           <h1>Product Name</h1>
           <h3> <%= product.getProductName() %></h3>
           <h1>Product Description</h1>
           <h3> <%= product.getDescription() %></h3>
           <h1>Product Price</h1>
           <h3> <%= product.getPrice() %></h3>
           <h1>Category</h1>
           <h3><%= product.getCategoryName() %></h3>
         
           
          
           
          
            
      </div>
    
    <h1>Product Image</h1>
    
    <%-- Convert the Blob data to bytes --%>
    <%
        byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
    %>
    
    <%-- Display the product image by encoding it in base64 --%>
    <img src="data:image/jpeg;base64, <%= new String(java.util.Base64.getEncoder().encode(imageData)) %>" alt="Product Image" />
    
    <%
        }
    %>
    
    <a href="HomeServlet">Back to Product List</a>
</body>
</html>