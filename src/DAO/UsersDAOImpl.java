package DAO;

import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersDAOImpl extends BaseDAO<Users>{
    public static Users currentUser = new Users();

    /**
     * User specific mapping to an observable list.
     * @param resultSet
     * @return
     * @throws SQLException
     */
    @Override
    public ObservableList<Users> map(ResultSet resultSet) throws SQLException {
        ObservableList<Users> userInfo = FXCollections.observableArrayList();

        while(resultSet.next()) {
            Users user = new Users();
            user.setUser_Name(resultSet.getString("User_Name"));
            user.setPassword(resultSet.getString("Password"));
            user.setUser_ID(resultSet.getInt("User_ID"));

            userInfo.add(user);
        }
        return userInfo;
    }

    /**
     * Returns the current user, which is set on the initial log in to establish who the current logged in user is.
     * @return
     */
    public Users getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets who the current logged in user is - utilized at login.
     * @param user
     */
    public void setCurrentUser(Users user){
        currentUser = user;
    }

    /**
     * User specific insert method - not used for this project.
     * @param newUser
     * @return
     * @throws SQLException
     */
    @Override
    public boolean insert(Users newUser) throws SQLException {
        //No need to add users
        return false;
    }

    /**
     * User specific update method - not used for this project.
     * @param updateUser
     * @return
     */
    @Override
    public boolean update(Users updateUser) {
        //No need to update users.
        return false;
    }

    /**
     * User specific delete method - not used for this project.
     * @param userToDelete
     * @return
     */
    @Override
    public boolean delete(Users userToDelete) {
        //No need to delete users.
        return false;
    }

    /**
     * Returns only a single user record from the database.
     * @param values
     * @return
     * @throws SQLException
     */
    @Override
    public ObservableList<Users> selectOne(String values) throws SQLException {
        ObservableList<Users> singleUser = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users WHERE " + values + ";";
        Statement stm = startStatement();
        stm.execute(sql);
        ResultSet resultSet = stm.getResultSet();

        while(resultSet.next()) {
            Users user = new Users();
            user.setUser_Name(resultSet.getString("User_Name"));
            user.setPassword(resultSet.getString("Password"));
            user.setUser_ID(resultSet.getInt("User_ID"));

            singleUser.add(user);
        }

        return singleUser;
    }
}
