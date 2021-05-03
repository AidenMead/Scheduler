package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ05Oij";

    private static final String jdbcURL = protocol + vendor + ipAddress ;

    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";

    private static final String username = "U05Oij";
    private static final String password = "53688559325";

    private static Connection conn = null;

    /**
     * Establishes the initial connection with the database
     * @return
     */
    public static Connection startConnection()
    {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection to DB Successful!");
        }
        catch(ClassNotFoundException e) {
            System.out.println("Class Error:" + e.getMessage());
        }
        catch(SQLException e) {
            System.out.println("SQL Error:" + e.getMessage());
        }
        return conn;
    }

    /**
     * Closes the connection with the database
     */
    public static void closeConnection()
    {
        try {
            conn.close();
            System.out.println("DB Connection Closed");
        }
        catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());

        }
    }

    /**
     * Returns the connection
     * @return
     */
    public static Connection getConnection(){
        return conn;
    }
}
