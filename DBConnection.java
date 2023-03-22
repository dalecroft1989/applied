package ca.nl.cna.java3.a1help;
import java.sql.*;

public class DBConnection {
    private static final String DB_URL = "jdbc:mariadb://localhost:3308/books";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    /**
     * Initializes a database connection and returns a Connection object.
     *
     * @return Connection object representing the database connection.
     * @throws SQLException if there is an error connecting to the database.
     */
    public static Connection initDatabase() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(DBConnection.DB_URL, DBConnection.DB_USER, DBConnection.DB_PASSWORD);
    }
}
