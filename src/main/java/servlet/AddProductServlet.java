
package servlet;


import DatabaseManager.DatabaseManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/add-product")
public class AddProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Display the add product form
        request.getRequestDispatcher("/add-product-form.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters for the new product
        String productName = request.getParameter("productName");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        // Additional processing for multiple images can be added here

        // Insert the new product into the database (you'll need a DatabaseManager class)
        int newlyCreatedProductId = DatabaseManager.addProduct(productName, description, price, categoryId);

        if (newlyCreatedProductId != -1) {
            // Redirect to the product details page for the new product
            response.sendRedirect("product-details?productId=" + newlyCreatedProductId);
        } else {
            // Handle the case where the product insertion failed
            response.sendRedirect("add-product?error=1");
        }
    }
}