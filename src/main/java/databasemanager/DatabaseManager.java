
package databasemanager;

import model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;




public class DatabaseManager {



    private static final String DB_URL = "jdbc:mysql://localhost:3306/JavaWebAppDatabase?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String DB_USER = "jeff";
    private static final String DB_PASSWORD = "jeff";

  
    
public static Connection getConnection() throws SQLException {
    Connection connection = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    } catch (ClassNotFoundException e) {
        throw new RuntimeException("Database driver not found.", e);
    } catch (SQLException e) {
        throw new RuntimeException("Connection to the database failed.", e);
    }

    return connection;
}



public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT * FROM products"; // Replace 'products' with your actual table name
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id"); // Replace with actual column names
                String productName = resultSet.getString("product_name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int categoryId = resultSet.getInt("category_id");

                // Create a Product object and add it to the list
                Product product = new Product(productId, productName, description, price, categoryId);
                products.add(product);
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

        return products;
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
            int categoryId = resultSet.getInt("category_id");

            // Create a Product object with the retrieved details
            product = new Product(productId, productName, description, price, categoryId);
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
public static boolean addProduct(Product product) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
        connection = getConnection();
        String query = "INSERT INTO products (product_name, description, price, category_id) VALUES (?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(query);

        // Set the values for the placeholders in the SQL query
        preparedStatement.setString(1, product.getProductName());
        preparedStatement.setString(2, product.getDescription());
        preparedStatement.setDouble(3, product.getPrice());
        preparedStatement.setInt(4, product.getCategoryId());

        // Execute the INSERT statement
        int rowsAffected = preparedStatement.executeUpdate();

        // Check if the product was added successfully (at least one row affected)
        if (rowsAffected > 0) {
            return true; // Product added successfully
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception as needed
    } finally {
        // Close resources
        try {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    return false; // Product not added or an error occurred
}

}

  
