<%-- 
    Document   : add_images
    Created on : 09 Oct 2023, 21:44:53
    Author     : jeff
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Upload Product Images</title>
</head>
<body>
    <h1>Upload Product Images</h1>
    <form action="UploadImagesServlet" method="post" enctype="multipart/form-data">
        <input type="hidden" name="product_id" value="${product_id}">
        <input type="file" name="images" multiple="multiple">
        <input type="submit" value="Upload">
    </form>
</body>
</html>
