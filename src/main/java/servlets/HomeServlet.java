package servlets;



import static databasemanager.DatabaseManager.getConnection;
import model.Product;
import java.io.IOException;
import java.sql.Connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;


public class HomeServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;


     
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Retrieve products from the database using DatabaseManager
            List<Product> products = getProducts();

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


  protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    try {
        // Retrieve search parameters from the request
        String productName = request.getParameter("searchQuery");
        String categoryName =  request.getParameter("categoryName");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        double minPrice = Double.parseDouble(minPriceStr);
        double maxPrice = Double.parseDouble(maxPriceStr);

        
 
          List<Product> searchResults = new ArrayList<>();
        
        if (!productName.isEmpty()) {
    searchResults = searchByName(productName);
        }
        else if (!categoryName.isEmpty()) {
    searchResults = searchByCategory(categoryName);
}
       else if (minPrice != 0.0 && maxPrice != 0.0) {
    searchResults = searchByPriceRange(minPrice, maxPrice);
}
     request.setAttribute("products", searchResults);

        // Forward the request to the "home.jsp" page for display
        request.getRequestDispatcher("/JSP/home.jsp").forward(request, response);
        
    } catch (Exception e) {
        e.printStackTrace();
        // Handle exceptions as needed
    }
    
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