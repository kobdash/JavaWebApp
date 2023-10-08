
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
                String categoryName = resultSet.getString("category_name");

                // Create a Product object and add it to the list
                Product product = new Product(productId, productName, description, price, categoryName);
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

public static List<String> getProductImages(int productId) {
    List<String> imageUrls = new ArrayList<>();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        connection = getConnection();
        String query = "SELECT image_url FROM product_images WHERE product_id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, productId);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String imageUrl = resultSet.getString("image_url");
            imageUrls.add(imageUrl);
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

    return imageUrls;
}


public static boolean addProduct(Product product, List<String> imageUrls, String categoryName) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
        connection = getConnection();
        connection.setAutoCommit(false); // Enable transaction

        // First, add the product to the 'products' table
        String insertProductSQL = "INSERT INTO products (product_name, description, price, category_id) VALUES (?, ?, ?, (SELECT category_id FROM categories WHERE category_name = ?))";
        preparedStatement = connection.prepareStatement(insertProductSQL, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, product.getProductName());
        preparedStatement.setString(2, product.getDescription());
        preparedStatement.setDouble(3, product.getPrice());
        preparedStatement.setString(4, categoryName);

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected == 0) {
            throw new SQLException("Creating product failed, no rows affected.");
        }

        // Retrieve the auto-generated product ID
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        int productId = -1;
        if (generatedKeys.next()) {
            productId = generatedKeys.getInt(1);
        } else {
            throw new SQLException("Creating product failed, no ID obtained.");
        }

        // Next, add the image URLs to the 'product_images' table
        String insertImageSQL = "INSERT INTO product_images (product_id, image_url) VALUES (?, ?)";
        preparedStatement = connection.prepareStatement(insertImageSQL);

        for (String imageUrl : imageUrls) {
            preparedStatement.setInt(1, productId);
            preparedStatement.setString(2, imageUrl);
            preparedStatement.addBatch();
        }

        int[] imageInsertCounts = preparedStatement.executeBatch();

        // Commit the transaction
        connection.commit();

        // Check if the image insert counts are valid
        for (int count : imageInsertCounts) {
            if (count != 1) {
                throw new SQLException("Adding image URLs failed, invalid insert count.");
            }
        }

        return true; // Product and images added successfully
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
            if (connection != null) {
                connection.setAutoCommit(true); // Revert to auto-commit mode
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return false; // Product and/or images not added or an error occurred
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


public static List<Product> searchByName(String productName) {
    List<Product> searchResults = new ArrayList<>();

    try (Connection connection = getConnection()) {
        String sql = "SELECT * FROM products WHERE LOWER(product_name) LIKE LOWER(?)";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set the search parameter with wildcard characters (%) to perform a partial match
            preparedStatement.setString(1, "%" + productName + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productNameFromDB = resultSet.getString("product_name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                String categoryNameFromDB = resultSet.getString("category_name");

                Product product = new Product(productId, productNameFromDB, description, price, categoryNameFromDB);
                searchResults.add(product);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle database-related exceptions here
    }

    return searchResults;
}

public static List<Product> searchByCategory(String categoryName) {
    List<Product> searchResults = new ArrayList<>();

    try (Connection connection = getConnection()) {
        String sql = "SELECT * FROM products WHERE category_id = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, categoryName);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
               String categoryNameFromDB = resultSet.getString("category_name");

              Product product = new Product(productId, productName, description, price, categoryNameFromDB);
                searchResults.add(product);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle database-related exceptions here
    }

    return searchResults;
}
public static List<Product> searchByPriceRange(double minPrice, double maxPrice) {
    List<Product> searchResults = new ArrayList<>();

    try (Connection connection = getConnection()) {
        String sql = "SELECT * FROM products WHERE price >= ? AND price <= ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, minPrice);
            preparedStatement.setDouble(2, maxPrice);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                String categoryNameFromDB = resultSet.getString("category_name");

                 Product product = new Product(productId, productName, description, price, categoryNameFromDB);
                searchResults.add(product);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle database-related exceptions here
    }

    return searchResults;
}
 

}

  
