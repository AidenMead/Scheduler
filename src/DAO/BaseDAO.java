package DAO;

import Utility.DBQuery;
import javafx.collections.ObservableList;

import java.sql.*;

public abstract class BaseDAO<T> {

    /**
     * Creates and sets a regular statement.
     * @return
     * @throws SQLException
     */
    public Statement startStatement() throws SQLException {
        DBQuery.setStatement();
        Statement stm = DBQuery.getStatement();

        return stm;
    }

    /**
     * Selects all of the rows for the table name passed as the parameter.
     * @param table
     * @return
     * @throws SQLException
     */
    public ObservableList getAll(String table) throws SQLException {
        String query = "SELECT * FROM " + table + ";";

        Statement stm = startStatement();
        stm.execute(query);
        ResultSet resultSet = stm.getResultSet();
        return map(resultSet);
    }

    /**
     * Takes a query as a parameter, executes the query, and returns the count of rows effected.
     * @param sql
     * @return
     * @throws SQLException
     */
    public int sendPreparedStm(String sql) throws SQLException {
        DBQuery.setPreparedStatement(sql);

        PreparedStatement prpstm = DBQuery.getPreparedStatement();
        prpstm.executeUpdate();
        return prpstm.getUpdateCount();
    }

    /**
     * A method that takes a query as a string, executes it as a prepared statement, and returns true if there are rows effected.
     * @param sql
     * @return
     * @throws SQLException
     */
    public boolean tryCatchQuery(String sql) throws SQLException {
        try {
            var stm = sendPreparedStm(sql);
            if(stm > 0) {
                return true;
            };
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * Abstract class that will be overridden. Maps table specific data to an observable list
     * and returns it. Primarily used for getAll().
     * @param resultSet
     * @return
     * @throws SQLException
     */
    public abstract ObservableList map(ResultSet resultSet) throws SQLException;

    /**
     * Abstract base for insert query to be overridden in concrete class.
     * @param t
     * @return
     * @throws SQLException
     */
    public abstract boolean insert(T t) throws SQLException;

    /**
     * Abstract base for insert query to be overridden in concrete class.
     * @param t
     * @return
     * @throws SQLException
     */
    public abstract boolean update(T t) throws SQLException;

    /**
     * Abstract base for insert query to be overridden in concrete class.
     * @param t
     * @return
     * @throws SQLException
     */
    public abstract boolean delete(T t) throws SQLException;

    /**
     * Abstract base for insert query to be overridden in concrete class.
     * @param values
     * @return
     * @throws SQLException
     */
    public abstract Object selectOne(String values) throws SQLException;
}
