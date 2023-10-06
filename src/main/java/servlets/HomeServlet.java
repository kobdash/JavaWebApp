package servlets;



import databasemanager.DatabaseManager;
import model.Product;
import java.io.IOException;
import java.sql.Connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;


public class HomeServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;


     
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Retrieve products from the database using DatabaseManager
            List<Product> products = DatabaseManager.getProducts();

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
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        double minPrice = Double.parseDouble(minPriceStr);
        double maxPrice = Double.parseDouble(maxPriceStr);

        
 
          List<Product> searchResults = new ArrayList<>();
        
        if (!productName.isEmpty()) {
    searchResults = DatabaseManager.searchByName(productName);
        }
        else if (categoryId != 0) {
    searchResults = DatabaseManager.searchByCategory(categoryId);
}
       else if (minPrice != 0.0 && maxPrice != 0.0) {
    searchResults = DatabaseManager.searchByPriceRange(minPrice, maxPrice);
}
     request.setAttribute("products", searchResults);

        // Forward the request to the "home.jsp" page for display
        request.getRequestDispatcher("/JSP/home.jsp").forward(request, response);
        
    } catch (Exception e) {
        e.printStackTrace();
        // Handle exceptions as needed
    }
    
}
 
}