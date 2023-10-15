package servlets;

import databasemanager.DatabaseManager;
import static databasemanager.DatabaseManager.getConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
        maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 100)
public class AddImagesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward the request to the JSP for rendering the form
        request.getRequestDispatcher("/JSP/add_images.jsp").forward(request, response);
    }

 
     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int productId = Integer.parseInt(request.getParameter("product_id"));
        List<Part> parts = (List<Part>) request.getParts();

        try (Connection connection = DatabaseManager.getConnection()) {
            for (Part part : parts) {
                if (part.getContentType() != null && part.getSize() > 0) {
                    String sql = "INSERT INTO product_images (product_id, image_data) VALUES (?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        preparedStatement.setInt(1, productId);
                        preparedStatement.setBinaryStream(2, part.getInputStream(), (int) part.getSize());
                        preparedStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions here
        }

        // Redirect back to the product details page or any other page
        response.sendRedirect("product_details.jsp?product_id=" + productId);
    }
     
     
     
     public static int getLatestProductID() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    int productId = -1; // Default value if not found

    try {
        connection = getConnection();

        // Query to retrieve the ID of the most recently added product
        String selectLatestProductIDSQL = "SELECT MAX(product_id) AS product_id FROM your_table_name";
        preparedStatement = connection.prepareStatement(selectLatestProductIDSQL);
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

// Helper method to read the binary image data from a Part
private static byte[] readImagePart(Part imagePart) throws IOException {
    try (InputStream input = imagePart.getInputStream();
         ByteArrayOutputStream output = new ByteArrayOutputStream()) {
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }
}
public static boolean saveImagesForLatestProduct(List<Part> imageParts) throws IOException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
        connection = getConnection();
        connection.setAutoCommit(false); // Enable transaction

        // Get the ID of the latest product added
        int latestProductId = getLatestProductID();

        if (latestProductId == -1) {
            throw new SQLException("Latest product ID not found");
        }

        // Insert image data into the 'product_images' table
        String insertImageSQL = "INSERT INTO product_images (product_id, image_data) VALUES (?, ?)";
        preparedStatement = connection.prepareStatement(insertImageSQL);

        for (Part imagePart : imageParts) {
            byte[] imageBytes = readImagePart(imagePart);
            preparedStatement.setInt(1, latestProductId);
            preparedStatement.setBytes(2, imageBytes);
            preparedStatement.addBatch();
        }

        int[] rowsAffected = preparedStatement.executeBatch();

        for (int rows : rowsAffected) {
            if (rows == 0) {
                throw new SQLException("Inserting image data failed");
            }
        }

        // Commit the transaction
        connection.commit();
        return true; // Images added successfully
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

    return false; // Error occurred or images not added
}

}
