package servlet;


import Model.Product;
import DatabaseManager.DatabaseManager;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the search query parameters from the request
        String query = request.getParameter("query");

        // Retrieve matching products from the database (you'll need a DatabaseManager class)
        List<Product> searchResults = DatabaseManager.searchProducts(query);

        // Set the search results as an attribute in the request
        request.setAttribute("searchResults", searchResults);
        request.setAttribute("query", query);

        // Forward the request to the search-results.jsp page
        request.getRequestDispatcher("/search-results.jsp").forward(request, response);
    }
}
