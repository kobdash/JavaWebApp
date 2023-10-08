
package servlets;

import model.Product;
import databasemanager.DatabaseManager;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.Collection;
import java.util.UUID;


public class AddProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/JSP/add-product.jsp").forward(request, response);
    }

     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Retrieve product details from the request
            String productName = request.getParameter("productName");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            String categoryName = request.getParameter("categoryName");
            
            // Create a list to store image URLs
            List<String> imageUrls = new ArrayList<>();
            
            // Retrieve the uploaded image files
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                if (part.getName().equals("images") && part.getSize() > 0) {
                    // Generate a unique file name for each image (e.g., using UUID)
                    String imageName = UUID.randomUUID().toString() + "_" + part.getSubmittedFileName();
                    
                    // Save the image to a directory (you should implement this)
                    String imagePath = saveImageToDirectory(part, imageName);
                    
                    // Add the image URL to the list
                    imageUrls.add(imagePath);
                }
            }

            // Create a new Product object
            Product newProduct = new Product(productName, description, price, categoryName);

            // Add the new product to the database using DatabaseManager
            boolean success = DatabaseManager.addProduct(newProduct, imageUrls, categoryName);

            /*if (success) {
                // Redirect to a success page or display a success message
                response.sendRedirect("success.jsp");
            } else {
                // Redirect to an error page or display an error message
                response.sendRedirect("error.jsp");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }
    
    // Implement a method to save the uploaded image to a directory
    private String saveImageToDirectory(Part part, String imageName) {
    try {
        // Define the directory where you want to save the images
        String uploadDirectory = "/home/jeff/NetBeansProjects/JavaWeb/src/main/webapp/images"; // Replace with your desired directory path
        
        // Create the directory if it doesn't exist
        File directory = new File(uploadDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // Construct the full path to save the image
        String imagePath = uploadDirectory + File.separator + imageName;
        
        // Open an output stream to write the image data to the file
        try (InputStream input = part.getInputStream();
             OutputStream output = new FileOutputStream(imagePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }

        return imagePath;
    } catch (IOException e) {
        e.printStackTrace();
        // Handle exceptions as needed (e.g., log the error)
        return null; // Return null to indicate failure
    }
}

    
}