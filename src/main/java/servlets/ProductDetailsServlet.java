
package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Blob;
import model.Product;
import databasemanager.DatabaseManager;
import jakarta.servlet.RequestDispatcher;

public class ProductDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the product ID from the request parameter
        String productIdStr = request.getParameter("productId");

        if (productIdStr != null) {
            try {
                int productId = Integer.parseInt(productIdStr);

                // Retrieve the product details and image data for the specified product
                Product product = getProductDetailsFromDatabase(productId);
                Blob imageBlob = getProductImageFromDatabase(productId);

                if (product != null && imageBlob != null) {
                    // Set product and imageBlob as attributes in the request
                    request.setAttribute("product", product);
                    request.setAttribute("imageBlob", imageBlob);

                    // Forward the request to a JSP for displaying both product details and the image
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/product-details.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.getWriter().write("Product not found or image not found for the product.");
                }
            } catch (NumberFormatException e) {
                response.getWriter().write("Invalid product ID.");
            }
        } else {
            response.getWriter().write("Product ID not provided.");
        }
    }

   private Product getProductDetailsFromDatabase(int productId) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Product product = null;

    try {
        connection = DatabaseManager.getConnection();

        // Query to retrieve product details based on the product ID
        String query = "SELECT product_id, product_name, description, price, category_name FROM products WHERE product_id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, productId);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            // Create a Product object with retrieved data
            product = new Product();
            product.setProductId(resultSet.getInt("product_id"));
            product.setProductName(resultSet.getString("product_name"));
            product.setDescription(resultSet.getString("description"));
            product.setPrice(resultSet.getDouble("price"));
            product.setCategoryName(resultSet.getString("category_name"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle database-related exceptions here
    } finally {
        // Close resources
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return product; 
}

    private Blob getProductImageFromDatabase(int productId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseManager.getConnection();

            // Query to retrieve the image data based on the product ID
            String query = "SELECT image_data FROM product_images WHERE product_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, productId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBlob("image_data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions here
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null; 
    }
}
