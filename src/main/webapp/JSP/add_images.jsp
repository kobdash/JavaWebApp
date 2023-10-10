<%-- 
    Document   : add_images
    Created on : 09 Oct 2023, 21:44:53
    Author     : jeff
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add images</title>
    </head>
    <body>
        
   <form action="AddImagesServlet" method="post"  enctype="multipart/form-data">
    <input type="file" name="images[]" multiple>
    <input type="submit" value="Upload Images">
   </form>
        
    </body>
</html>
