
package DatabaseManager;

import Model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;




public class DatabaseManager {



    private static final String DB_URL = "jdbc:mysql://localhost:3306/JavaWeAppDatabase";
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

    public static List<Product> searchProducts(String query) {
        List<Product> searchResults = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM products WHERE product_name LIKE ?")) {

            preparedStatement.setString(1, "%" + query + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setProductId(resultSet.getInt("product_id"));
                    product.setProductName(resultSet.getString("product_name"));
                    product.setDescription(resultSet.getString("description"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setCategoryId(resultSet.getInt("category_id"));
                    searchResults.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related errors here
        }

        return searchResults;
    }
    
    
    
     public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM products");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("product_id"));
                product.setProductName(resultSet.getString("product_name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setCategoryId(resultSet.getInt("category_id"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related errors here
        }

        return products;
    }

    // Existing methods for other database operations
public static Product getProductById(int productId) {
        Product product = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM products WHERE product_id = ?")) {

            preparedStatement.setInt(1, productId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    product = new Product();
                    product.setProductId(resultSet.getInt("product_id"));
                    product.setProductName(resultSet.getString("product_name"));
                    product.setDescription(resultSet.getString("description"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setCategoryId(resultSet.getInt("category_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related errors here
        }

        return product;
    }

    // Get related products (placeholder method)
    public static List<Product> getRelatedProducts(int productId) {
        List<Product> relatedProducts = new ArrayList<>();

        // Implement logic to retrieve related products based on your criteria
        // You might want to fetch products from the same category or with similar attributes

        // Example placeholder code:
        // SELECT * FROM products WHERE category_id = ? AND product_id != ?

        // Make sure to set the related products into the 'relatedProducts' list

        return relatedProducts;
    }


    
    
    public static int addProduct(String productName, String description, double price, int categoryId) {
    // Define your database connection details here
    String url = "jdbc:mysql://localhost:3306/your_database";
    String username = "your_username";
    String password = "your_password";

    // SQL insert query for adding a new product
    String insertQuery = "INSERT INTO products (product_name, description, price, category_id) VALUES (?, ?, ?, ?)";

    try (Connection connection = DriverManager.getConnection(url, username, password);
         PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

        preparedStatement.setString(1, productName);
        preparedStatement.setString(2, description);
        preparedStatement.setDouble(3, price);
        preparedStatement.setInt(4, categoryId);

        int rowsAffected = preparedStatement.executeUpdate();

        // Check if the insertion was successful
        if (rowsAffected > 0) {
            // Retrieve the generated keys (the newly created product's ID)
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); // Return the newly created product's ID
            }
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Handle database-related errors here
    }

    return -1; // Return -1 to indicate that the insertion failed
}
}
