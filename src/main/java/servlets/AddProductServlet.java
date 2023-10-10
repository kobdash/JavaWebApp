package servlets;

import model.Product;
import databasemanager.DatabaseManager;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;



   public class AddProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward the request to the JSP for rendering the form
        request.getRequestDispatcher("/JSP/add-product.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve product information from the request parameters
        String productName = request.getParameter("productName");
        String description = request.getParameter("description");
        String pricestr = request.getParameter("price");
        double price = Double.parseDouble(pricestr);
        String categoryName = request.getParameter("categoryName");
       

  

        // Add the product and image data to the database
        Product product = new Product(productName, description, price, categoryName);
        boolean productAdded = DatabaseManager.addProductToProductsTable(product);

        if (productAdded) {
            // Save the image data as a LONGBLOB in the database
            int productId = DatabaseManager.getProductID(productName); // Retrieve the product I
                response.getWriter().write("Product added successfully!");
                request.getRequestDispatcher("/JSP/add_images.jsp").forward(request, response);
            } else {
                response.getWriter().write("Error saving the image data.");
            }
 
       
    }
}



    
