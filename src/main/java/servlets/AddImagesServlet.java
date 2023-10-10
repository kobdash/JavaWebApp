
package servlets;

import databasemanager.DatabaseManager;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;



import jakarta.servlet.annotation.MultipartConfig;


@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// 10 MB 
                 maxFileSize=1024*1024*50,      	// 50 MB
                 maxRequestSize=1024*1024*100) 


public class AddImagesServlet extends HttpServlet {

    


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward the request to the JSP for rendering the form
        request.getRequestDispatcher("/JSP/add-images.jsp").forward(request, response);
    }
    
       protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
       int productId = DatabaseManager.getMostRecentlyAddedProductID();

    // Handle multiple file uploads
             Collection<Part> imageParts = request.getParts();
                for (Part imagePart : imageParts) {
        if (imagePart.getName().startsWith("images")) {
            // Read the image data from the Part into an InputStream
            InputStream imageInputStream = imagePart.getInputStream();

            // Insert the image data into the product_images table along with the product ID
            boolean imageSaved = DatabaseManager.saveImageToProductImages(productId, imageInputStream);
                        response.getWriter().write("Product added successfully!");
            if (!imageSaved) {
                // Handle the case where an image failed to save
            }
        }

                }}
}