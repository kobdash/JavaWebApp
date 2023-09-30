package servlets;



import databasemanager.DatabaseManager;
import model.Product;
import java.io.IOException;
import java.sql.Connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;


public class HomeServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;
     
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Retrieve products from the database using DatabaseManager
            List<Product> products = DatabaseManager.getProducts();

            // Set the products as an attribute in the request
            request.setAttribute("products", products);

            // Forward the request to the JSP page for display
            request.getRequestDispatcher("/JSP/home.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    // ... (other methods and code)


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST requests if needed
    }
 
}