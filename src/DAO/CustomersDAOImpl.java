package DAO;

import Model.Appointments;
import Model.Customers;
import Utility.DBConnection;
import Utility.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class CustomersDAOImpl extends BaseDAO<Customers> {

    public static int nextCustId;

    /**
     * Customers specific mapping method.
     * @param resultSet
     * @return
     * @throws SQLException
     */
    @Override
    public ObservableList<Customers> map(ResultSet resultSet) throws SQLException {
        ObservableList<Customers> customers = FXCollections.observableArrayList();

        while(resultSet.next()){
            Customers customer = new Customers();

            customer.setCustomer_ID(resultSet.getInt("Customer_ID"));
            customer.setCustomer_Name(resultSet.getString("Customer_Name"));
            customer.setAddress(resultSet.getString("Address"));
            customer.setPostal_Code(resultSet.getString("Postal_Code"));
            customer.setDivision_ID(resultSet.getInt("Division_ID"));
            customer.setPhone(resultSet.getString("Phone"));

            customers.add(customer);
        }
        return customers;
    }

    /**
     * Customers specific insert method used to add new customer to the database.
     * @param newCustomer
     * @return
     * @throws SQLException
     */
    @Override
    public boolean insert(Customers newCustomer) throws SQLException {
        String insertQuery = "INSERT INTO customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int id = newCustomer.getCustomer_ID();
        String name = newCustomer.getCustomer_Name();
        String address = newCustomer.getAddress();
        String postcode = newCustomer.getPostal_Code();
        String phone = newCustomer.getPhone();
        LocalDateTime createDate = newCustomer.getCreate_Date();
        String createdBy = newCustomer.getCreated_By();
        LocalDateTime lastUpdate = newCustomer.getLast_Update();
        String lastUpdatedBy = newCustomer.getLast_Updated_By();
        int division = newCustomer.getDivision_ID();

        PreparedStatement prpstm = DBConnection.getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

        prpstm.setInt(1, id);
        prpstm.setString(2, name);
        prpstm.setString(3, address);
        prpstm.setString(4, postcode);
        prpstm.setString(5, phone);
        prpstm.setTimestamp(6, Timestamp.valueOf(createDate));
        prpstm.setString(7, createdBy);
        prpstm.setTimestamp(8, Timestamp.valueOf(lastUpdate));
        prpstm.setString(9, lastUpdatedBy);
        prpstm.setInt(10, division);

        try {
            prpstm.executeUpdate();
            ResultSet keys = prpstm.getGeneratedKeys();
            while (keys.next()) {
                nextCustId = Integer.parseInt(keys.getString(1));
            }
            if(prpstm.getUpdateCount() > 0){
                return true;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Customer specific update method used to alter an existing customer record.
     * @param updatedCustomer
     * @return
     * @throws SQLException
     */
    @Override
    public boolean update(Customers updatedCustomer) throws SQLException {
        int id = updatedCustomer.getCustomer_ID();
        String name = updatedCustomer.getCustomer_Name();
        String address = updatedCustomer.getAddress();
        String postcode = updatedCustomer.getPostal_Code();
        String phone = updatedCustomer.getPhone();
        LocalDateTime lastUpdate = updatedCustomer.getLast_Update();
        String lastUpdatedBy = updatedCustomer.getLast_Updated_By();
        int division = updatedCustomer.getDivision_ID();

        String updateQuery = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Update=?, Last_Updated_By=?, Division_ID=? WHERE Customer_ID=?;";

        DBQuery.setPreparedStatement(updateQuery);
        PreparedStatement prpstm = DBQuery.getPreparedStatement();

        prpstm.setString(1, name);
        prpstm.setString(2, address);
        prpstm.setString(3, postcode);
        prpstm.setString(4, phone);
        prpstm.setTimestamp(5, Timestamp.valueOf(lastUpdate));
        prpstm.setString(6, lastUpdatedBy);
        prpstm.setInt(7, division);
        prpstm.setInt(8, id);

        try {
            prpstm.executeUpdate();
            if(prpstm.getUpdateCount() > 0){
                return true;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Customer specific delete method which deletes a customer record.
     * @param custToDelete
     * @return
     * @throws SQLException
     */
    @Override
    public boolean delete(Customers custToDelete) throws SQLException {
        int id = custToDelete.getCustomer_ID();;

        String deleteQuery = "DELETE FROM customers WHERE Customer_ID=? ;";

        DBQuery.setPreparedStatement(deleteQuery);
        PreparedStatement prpstm = DBQuery.getPreparedStatement();

        prpstm.setInt(1, id);

        try{
            prpstm.executeUpdate();
            if(prpstm.getUpdateCount() >0){
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Selects a single record from the database.
     * @param values
     * @return
     * @throws SQLException
     */
    @Override
    public ResultSet selectOne(String values) throws SQLException {
        String sql = "SELECT * FROM customers " + values + ";";

        Statement stm = startStatement();
        stm.execute(sql);
        return stm.getResultSet();
    }

    /**
     * Queries for appointments that contain only a specific customer in the Customer_ID field.
     * @param customer
     * @return
     * @throws SQLException
     */
    public ObservableList findCustAppointments(Customers customer) throws SQLException {
        int id = customer.getCustomer_ID();
        String apptQuery = "SELECT * FROM appointments WHERE Customer_ID=?;";

        DBQuery.setPreparedStatement(apptQuery);
        PreparedStatement prpstm = DBQuery.getPreparedStatement();

        prpstm.setInt(1, id);

        ObservableList associatedAppointments = FXCollections.observableArrayList();
        try{
            prpstm.execute();
            ResultSet rs = prpstm.getResultSet();
            while(rs.next()){
                Appointments appointment = new Appointments();
                appointment.setAppointment_ID(rs.getInt("Appointment_ID"));
                appointment.setTitle(rs.getString("Title"));
                appointment.setType(rs.getString("Type"));
                appointment.setDescription(rs.getString("Description"));
                appointment.setStart(rs.getTimestamp("Start").toLocalDateTime());
                appointment.setEnd(rs.getTimestamp("End").toLocalDateTime());
                appointment.setCustomer_ID(rs.getInt("Customer_ID"));

                associatedAppointments.add(appointment);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return associatedAppointments;
    }

}
