package servlet;



import DatabaseManager.DatabaseManager;
import Model.Product;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
public void init() throws ServletException {
    super.init();

    // Attempt to establish a database connection during servlet initialization
    try {
        Connection connection = DatabaseManager.getConnection();
        if (connection == null) {
            // If the connection is null, throw a ServletException indicating a database error
            String errorMessage = "Database connection error";
            getServletContext().setAttribute("errorMessage", "fccck");
            throw new ServletException(errorMessage);
        }
    } catch (Exception e) {
        // If a SQLException occurs, throw a ServletException with the root cause
        String errorMessage = "Database error";
        getServletContext().setAttribute("errorMessage", "fcccck");
        throw new ServletException(errorMessage, e);
    }
}

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Retrieve a list of products from the database
            List<Product> products = DatabaseManager.getAllProducts();

            // Set the list of products as an attribute in the request
            request.setAttribute("products", products);

            // Forward the request to the home.jsp page
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        } catch (Exception e) {
            // Handle any SQLExceptions that occur during database operations
            e.printStackTrace(); // For debugging purposes; consider logging instead

            // Set an error message as an attribute in the request
            request.setAttribute("errorMessage", "An error occurred while fetching products.");

            // Forward the request to the home.jsp page to display the error message
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        }
    }
}