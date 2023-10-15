package servlets;

import static databasemanager.DatabaseManager.getConnection;
import model.Product;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



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
        boolean productAdded = addProductToProductsTable(product);

        if (productAdded) {
                response.getWriter().write("Product added successfully!");
                request.getRequestDispatcher("/JSP/add_images.jsp").forward(request, response);
            } else {
                response.getWriter().write("Error saving the image data.");
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
   public static boolean addProductToCategoriesTable(String categoryName) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
        connection = getConnection();

        // Add the category to the 'categories' table
        String insertCategorySQL = "INSERT INTO categories (category_name) VALUES (?)";
        preparedStatement = connection.prepareStatement(insertCategorySQL);

        preparedStatement.setString(1, categoryName);

        int rowsAffected = preparedStatement.executeUpdate();

        // Check if the category was added successfully
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

    return false; // Error occurred or category not added
}
 
    public static List<String> retrieveCategoriesFromDatabase() {
        List<String> categories = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT category_name FROM categories"; // Replace with your table name and column name
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String categoryName = resultSet.getString("category_name"); // Replace with your column name
                categories.add(categoryName);
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
                // Handle the exception as needed
            }
        }

        return categories;
    } 
    
}



    
