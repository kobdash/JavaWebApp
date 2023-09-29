package servlet;



import DatabaseManager.DatabaseManager;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.Product;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve a list of products from the database (you'll need a DatabaseManager class)
        List<Product> products = DatabaseManager.getAllProducts();

        // Set the list of products as an attribute in the request
        request.setAttribute("products", products);

        // Forward the request to the home.jsp page
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}