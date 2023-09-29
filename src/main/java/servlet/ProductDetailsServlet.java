package servlet;

import Model.Product;
import DatabaseManager.DatabaseManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/product-details")
public class ProductDetailsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the productId from the request parameter
        int productId = Integer.parseInt(request.getParameter("productId"));

        // Retrieve the product details from the database (you'll need a DatabaseManager class)
        Product product = DatabaseManager.getProductById(productId);
        
        if (product != null) {
            // Retrieve related products (you'll need a method to get related products)
            List<Product> relatedProducts = DatabaseManager.getRelatedProducts(productId);

            // Set the product and related products as attributes in the request
            request.setAttribute("product", product);
            request.setAttribute("relatedProducts", relatedProducts);

            // Forward the request to the product-details.jsp page
            request.getRequestDispatcher("/product-details.jsp").forward(request, response);
        } else {
            // Handle the case where the product with the specified ID is not found
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
        }
    }
}