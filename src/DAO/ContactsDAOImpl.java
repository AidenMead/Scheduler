package DAO;

import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import Utility.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContactsDAOImpl extends BaseDAO<Contacts>{

    /**
     * Contact specific mapping to an observable list.
     * @param resultSet
     * @return
     * @throws SQLException
     */
    @Override
    public ObservableList map(ResultSet resultSet) throws SQLException {
        ObservableList<Contacts> contacts = FXCollections.observableArrayList();

        while(resultSet.next()){
            Contacts contact = new Contacts();
            contact.setContact_ID(resultSet.getInt("Contact_ID"));
            contact.setContact_Name(resultSet.getString("Contact_Name"));
            contact.setEmail(resultSet.getString("Email"));

            contacts.add(contact);
        }

        return contacts;
    }

    /**
     * Overriden insert method - not used for this project for contacts
     * @param newContact
     * @return
     * @throws SQLException
     */
    @Override
    public boolean insert(Contacts newContact) throws SQLException {
        //Not within scope of project
        return false;
    }

    /**
     * Overridden update method - not used for this project for contacts
     * @param updatedContact
     * @return
     * @throws SQLException
     */
    @Override
    public boolean update(Contacts updatedContact) throws SQLException {
        //Not within scope of project
        return false;
    }

    /**
     * Overridden delete method - not used for this project for contacts
     * @param contactToDelete
     * @return
     */
    @Override
    public boolean delete(Contacts contactToDelete) {
        //Not within scope of project
        return false;
    }

    /**
     * Overridden selectOne - not used anywhere, but made as proof of concept.
     * @param values
     * @return
     * @throws SQLException
     */
    @Override
    public ResultSet selectOne(String values) throws SQLException {
        String sql = "SELECT * FROM contacts " + values + ";";

        Statement stm = startStatement();
        stm.execute(sql);
        ResultSet resultSet = stm.getResultSet();
        return resultSet;
    }


    public ObservableList findContactAppointments(Contacts contact) throws SQLException {
        int id = contact.getContact_ID();
        String apptQuery = "SELECT * FROM appointments WHERE Contact_ID=?;";

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
                appointment.setContact_ID(rs.getInt("Contact_ID"));
                appointment.setCustomer_ID(rs.getInt("Customer_ID"));

                associatedAppointments.add(appointment);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return associatedAppointments;
    }
}
