package servlets;

import static databasemanager.DatabaseManager.getConnection;
import model.Product;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




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
    String categoryName= request.getParameter("categoryName");
   
    

// Handle image upload
    Part filePart = request.getPart("image");
    InputStream imageInputStream = filePart.getInputStream();

    // Add the product and image data to the database
    Product product = new Product(productName, description, price, categoryName);

    boolean productAdded = addProductToProductsTable(product);

    if (productAdded) {
        int productId = getProductIDFromDatabase(productName); // Implement this method to retrieve the product ID

        boolean imageAdded = addProductImageToTable(productId, imageInputStream);

        if (imageAdded) {
           
            response.getWriter().write("Product and image added successfully!");
            
          
        } else {
            response.getWriter().write("Error saving the image data.");
        }
    } else {
        response.getWriter().write("Error adding the product.");
    }
}
    
    public static boolean addProductToProductsTable(Product product) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet generatedKeys = null;

    try {
        connection = getConnection();
        connection.setAutoCommit(false); // Enable transaction

        // Add the product to the 'products' table
        String insertProductSQL = "INSERT INTO products (product_name, description, price, category_name) VALUES (?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(insertProductSQL, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, product.getProductName());
        preparedStatement.setString(2, product.getDescription());
        preparedStatement.setDouble(3, product.getPrice());
        preparedStatement.setString(4, product.getCategoryName());

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected == 0) {
            throw new SQLException("Creating product failed, no rows affected.");
        }

        // Retrieve the auto-generated product ID
        generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            // Commit the transaction
            connection.commit();
            return true; // Product added successfully
        } else {
            throw new SQLException("Creating product failed, no ID obtained.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle database-related exceptions here
        try {
            if (connection != null) {
                connection.rollback(); // Rollback the transaction in case of an error
            }
        } catch (SQLException rollbackEx) {
            rollbackEx.printStackTrace();
        }
    } finally {
        // Close resources
        try {
            if (preparedStatement != null) preparedStatement.close();
            if (generatedKeys != null) generatedKeys.close();
            if (connection != null) {
                connection.setAutoCommit(true); // Revert to auto-commit mode
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return false; // Error occurred or product not added
}

 
    public static int getProductIDFromDatabase(String productName) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    int productId = -1; // Initialize to a default value

    try {
        connection = getConnection();

        // Query to retrieve the product ID based on the product name
        String query = "SELECT product_id FROM products WHERE product_name = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, productName);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            productId = resultSet.getInt("product_id");
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

    return productId;
}
    
    public static boolean addProductImageToTable(int productId, InputStream imageInputStream) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
        connection = getConnection();

        // Query to insert image data into the "product_images" table
        String insertImageSQL = "INSERT INTO product_images (image_data, product_id) VALUES (?, ?)";
        preparedStatement = connection.prepareStatement(insertImageSQL);

        preparedStatement.setBlob(1, imageInputStream);
        preparedStatement.setInt(2, productId);

        int rowsAffected = preparedStatement.executeUpdate();

        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle database-related exceptions here
    } finally {
        // Close resources
        try {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return false; // Error occurred or image not added
}
    
    
}



    
