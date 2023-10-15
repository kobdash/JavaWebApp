
package servlets;

import model.Product;
import static databasemanager.DatabaseManager.getConnection;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class ProductDetailsServlet extends HttpServlet {
      private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Extract the product ID from the request
            int productId = Integer.parseInt(request.getParameter("productId"));

            // Retrieve the product details by productId (You need to implement this logic)
            Product product = getProductDetails(productId);

            // Set the product details as an attribute in the request
            request.setAttribute("product", product);

            // Forward the request to a JSP page for displaying product details
            request.getRequestDispatcher("/JSP/product-details.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }
    
    public static Product getProductDetails(int productId) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Product product = null;

    try {
        connection = getConnection();
        String query = "SELECT * FROM products WHERE product_id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, productId);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            // Retrieve product details
            String productName = resultSet.getString("product_name");
            String description = resultSet.getString("description");
            double price = resultSet.getDouble("price");
            String categoryName = resultSet.getString("category_name");

            // Create a Product object with the retrieved details
            product = new Product(productId, productName, description, price, categoryName);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception as needed
    } finally {
        // Close resources
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    return product;
}

}