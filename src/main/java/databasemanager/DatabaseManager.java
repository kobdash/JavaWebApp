
package databasemanager;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;





public class DatabaseManager {



    private static final String DB_URL = "jdbc:mysql://localhost:3306/JavaWebDB?zeroDateTimeBehavior=CONVERT_TO_NULL";
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




}