
package servlets;


import model.Product;
import databasemanager.DatabaseManager;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));

            // Create a new Product object
            Product newProduct = new Product(productName, description, price, categoryId);

            // Add the new product to the database using DatabaseManager
            boolean success = DatabaseManager.addProduct(newProduct);

           /* if (success) {
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

    // ... (other methods and code)
}