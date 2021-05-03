package Utility;

import java.sql.*;

/**
 * Class to house query specific methods.
 */
public class DBQuery {
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    /**
     * Sets statement.
     * @throws SQLException
     */
    public static void setStatement() throws SQLException {
        statement = DBConnection.getConnection().createStatement();
    }

    /**
     * Returns the set statement.
     * @return
     */
    public static Statement getStatement()
    {
        return statement;
    }

    /**
     * Returns prepared statement.
     * @return
     */
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    /**
     * Sets prepared statement.
     * @param sql
     * @throws SQLException
     */
    public static void setPreparedStatement(String sql) throws SQLException {
        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
    }

}
