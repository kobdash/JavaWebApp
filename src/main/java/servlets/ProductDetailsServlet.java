package servlets;

import model.Product;
import databasemanager.DatabaseManager;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;


public class ProductDetailsServlet extends HttpServlet {
      private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Extract the product ID from the request
            int productId = Integer.parseInt(request.getParameter("productId"));

            // Retrieve the product details by productId (You need to implement this logic)
            Product product = DatabaseManager.getProductDetails(productId);

            // Set the product details as an attribute in the request
            request.setAttribute("product", product);

            // Forward the request to a JSP page for displaying product details
            request.getRequestDispatcher("/JSP/product-details.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

}