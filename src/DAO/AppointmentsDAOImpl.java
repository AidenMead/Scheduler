package DAO;

import Model.Appointments;

import Utility.DBConnection;
import Utility.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import java.time.LocalDateTime;

public class AppointmentsDAOImpl extends BaseDAO<Appointments> {
    public static int nextApptId;

    /**
     * Appointments specific map method to pasre the result set of the query in BaseDAO getAll() method.
     * @param resultSet
     * @return
     * @throws SQLException
     */
    @Override
    public ObservableList<Appointments> map(ResultSet resultSet) throws SQLException {
        ObservableList<Appointments> apptList = FXCollections.observableArrayList();

        while(resultSet.next()){
            Appointments appointment = new Appointments();

            appointment.setAppointment_ID(resultSet.getInt("Appointment_ID"));
            appointment.setTitle(resultSet.getString("Title"));
            appointment.setDescription(resultSet.getString("Description"));
            appointment.setLocation(resultSet.getString("Location"));
            appointment.setType(resultSet.getString("Type"));
            appointment.setStart(resultSet.getTimestamp("Start").toLocalDateTime());
            appointment.setEnd(resultSet.getTimestamp("End").toLocalDateTime());
            appointment.setCreate_Date(resultSet.getTimestamp("Create_Date").toLocalDateTime());
            appointment.setCreated_By(resultSet.getString("Created_By"));
            appointment.setLast_Update(resultSet.getTimestamp("Last_Update").toLocalDateTime());
            appointment.setLast_Updated_By(resultSet.getString("Last_Updated_By"));
            appointment.setCustomer_ID(resultSet.getInt("Customer_ID"));
            appointment.setUser_ID(resultSet.getInt("User_ID"));
            appointment.setContact_ID(resultSet.getInt("Contact_ID"));

            apptList.add(appointment);
        }

        return apptList;
    }

    /**
     * Handles specifics of inserting a new Appointment into the appointments table.
     * @param newAppointment
     * @return
     * @throws SQLException
     */
    @Override
    public boolean insert(Appointments newAppointment) throws SQLException {
        String addQuery = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement prpstm = DBConnection.getConnection().prepareStatement(addQuery, Statement.RETURN_GENERATED_KEYS);

        String title = newAppointment.getTitle();
        String description = newAppointment.getDescription();
        String location = newAppointment.getLocation();
        String type = newAppointment.getType();
        LocalDateTime start = newAppointment.getStart();
        LocalDateTime end = newAppointment.getEnd();
        LocalDateTime created_date = newAppointment.getCreate_Date();
        String created_by = newAppointment.getCreated_By();
        LocalDateTime last_update = newAppointment.getLast_Update();
        String last_updated_by = newAppointment.getLast_Updated_By();
        int customer_id = newAppointment.getCustomer_ID();
        int user_id = newAppointment.getUser_ID();
        int contact_id = newAppointment.getContact_ID();

        prpstm.setString(1, title);
        prpstm.setString(2, description);
        prpstm.setString(3, location);
        prpstm.setString(4, type);
        prpstm.setTimestamp(5, Timestamp.valueOf(start));
        prpstm.setTimestamp(6, Timestamp.valueOf(end));
        prpstm.setTimestamp(7, Timestamp.valueOf(created_date));
        prpstm.setString(8, created_by);
        prpstm.setTimestamp(9, Timestamp.valueOf(last_update));
        prpstm.setString(10, last_updated_by);
        prpstm.setInt(11, customer_id);
        prpstm.setInt(12, user_id);
        prpstm.setInt(13, contact_id);

        try{
            prpstm.executeUpdate();
            ResultSet rs = prpstm.getGeneratedKeys();
            while(rs.next()) {
                nextApptId = Integer.parseInt(rs.getString(1));
            }
            if(prpstm.getUpdateCount() > 0){
                return true;
            };
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    /**
     * Handles updating an appointment in the appointments table.
     * @param apptToUpdate
     * @return
     * @throws SQLException
     */
    @Override
    public boolean update(Appointments apptToUpdate) throws SQLException {
        String updateQuery = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Create_Date=?, Created_By=?, Last_Update=?, Last_Updated_By=?, Customer_ID=?, Contact_ID=? WHERE Appointment_ID = ?;";

        DBQuery.setPreparedStatement(updateQuery);
        PreparedStatement prpstm = DBQuery.getPreparedStatement();

        int apptId = apptToUpdate.getAppointment_ID();
        String title = apptToUpdate.getTitle();
        String description = apptToUpdate.getDescription();
        String location = apptToUpdate.getLocation();
        String type = apptToUpdate.getType();
        LocalDateTime start = apptToUpdate.getStart();
        LocalDateTime end = apptToUpdate.getEnd();
        LocalDateTime created_date = apptToUpdate.getCreate_Date();
        String created_by = apptToUpdate.getCreated_By();
        LocalDateTime last_update = apptToUpdate.getLast_Update();
        String last_updated_by = apptToUpdate.getLast_Updated_By();
        int customer_id = apptToUpdate.getCustomer_ID();
        int contact_id = apptToUpdate.getContact_ID();

        prpstm.setString(1, title);
        prpstm.setString(2, description);
        prpstm.setString(3, location);
        prpstm.setString(4, type);
        prpstm.setTimestamp(5, Timestamp.valueOf(start));
        prpstm.setTimestamp(6, Timestamp.valueOf(end));
        prpstm.setTimestamp(7, Timestamp.valueOf(created_date));
        prpstm.setString(8, created_by);
        prpstm.setTimestamp(9, Timestamp.valueOf(last_update));
        prpstm.setString(10, last_updated_by);
        prpstm.setInt(11, customer_id);
        prpstm.setInt(12, contact_id);
        prpstm.setInt(13, apptId);

        try{
            prpstm.executeUpdate();
            if(prpstm.getUpdateCount() > 0){
                return true;
            };
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    /**
     * Handles deleting an appointment by the appointment_id
     * @param apptToDelete
     * @return
     * @throws SQLException
     */
    @Override
    public boolean delete(Appointments apptToDelete) throws SQLException {
        int id = apptToDelete.getAppointment_ID();

        String deleteQuery = "DELETE FROM appointments WHERE Appointment_ID =? ;";

        DBQuery.setPreparedStatement(deleteQuery);
        PreparedStatement prpstm = DBQuery.getPreparedStatement();

        prpstm.setInt(1, id) ;

        try {
            prpstm.executeUpdate();
            if(prpstm.getUpdateCount()>0){
                return true;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return false;
    }

    /**
     * Selects only a single appointment from the DB.
     * @param values
     * @return
     * @throws SQLException
     */
    @Override
    public ResultSet selectOne(String values) throws SQLException {
        String sql = "SELECT * FROM appointments " + values + ";";

        Statement stm = startStatement();
        stm.execute(sql, Statement.RETURN_GENERATED_KEYS);
        return stm.getResultSet();
    }

    /**
     * Used for the specific report for the report that shows the appointments by month and type.
     * @return
     * @throws SQLException
     */
    public ObservableList getTypeMonthResults() throws SQLException {
        ObservableList typeMonthList = FXCollections.observableArrayList();

        String monthTypeQuery = "SELECT MONTHNAME(Start) as MONTH, type, COUNT(*) as COUNT FROM appointments GROUP BY MONTHNAME(Start) DESC, Type;";

        DBQuery.setPreparedStatement(monthTypeQuery);
        PreparedStatement prpstm = DBQuery.getPreparedStatement();

        ResultSet rs = null;
        try{
            prpstm.execute();
            rs = prpstm.getResultSet();

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        while(rs.next()){
            Appointments appt = new Appointments();

            appt.setMonth(rs.getString("Month"));
            appt.setType(rs.getString("Type"));
            appt.setCount(rs.getInt("Count"));

            typeMonthList.add(appt);
        }

        return typeMonthList;
    }
}
