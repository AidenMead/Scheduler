package DAO;

import Model.Appointments;
import Model.Countries;
import Utility.DBQuery;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Class to control the countries specific database interactions.
 */
public class CountriesDAOImpl extends BaseDAO<Countries> {

    /**
     * Maps the unique properties of the countries tables to be returned in the BaseDAO getAll() method.
     * @param resultSet
     * @return
     * @throws SQLException
     */
    @Override
    public ObservableList<Countries> map(ResultSet resultSet) throws SQLException {
        ObservableList<Countries> countryList = FXCollections.observableArrayList();

        while(resultSet.next())
        {
            Countries country = new Countries();

            country.setCountry(resultSet.getString("Country"));
            country.setCountry_ID(resultSet.getInt("Country_ID"));

            countryList.add(country);
        }
        return countryList;
    }

    /**
     * Inserts a new country into the table - not used for this project.
     * @param newCountry
     * @return
     * @throws SQLException
     */
    @Override
    public boolean insert(Countries newCountry) throws SQLException {
        String sql = "INSERT INTO countries(Country, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES(?, ?, ?, ?, ?);";

        DBQuery.setPreparedStatement(sql);
        PreparedStatement prpstm = DBQuery.getPreparedStatement();

        //get the values from the object that was passed as parameter
        String country_name = newCountry.getCountry();
        LocalDateTime createdDate = newCountry.getCreate_Date();
        String createdBy = newCountry.getCreated_By();
        LocalDateTime lastUpdate = newCountry.getLast_Update();
        String lastUpdatedBy = newCountry.getLast_Updated_By();

        prpstm.setString(1, country_name);
        prpstm.setTimestamp(2, Timestamp.valueOf(createdDate));
        prpstm.setString(3, createdBy);
        prpstm.setTimestamp(4, Timestamp.valueOf(lastUpdate));
        prpstm.setString(5, lastUpdatedBy);

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
     * Would house update date, but not used for countries for this project.
     * @param updatedCountry
     * @return
     * @throws SQLException
     */
    @Override
    public boolean update(Countries updatedCountry) throws SQLException {
        //Not used for this project.
        return false;
    }

    /**
     * Deletes a country from the table. Not needed for this project, but used to test the tryCatchQuery method.
     * @param countryToDelete
     * @return
     * @throws SQLException
     */
    @Override
    public boolean delete(Countries countryToDelete) throws SQLException {
        String sql = "DELETE FROM countries WHERE  Country_ID =?;";
        return tryCatchQuery(sql);
    }

    /**
     * Selects only a single country based on values fed into the method. Also uses the above map
     * method to parse the results.
     * @param values
     * @return
     * @throws SQLException
     */
    @Override
    public Object selectOne(String values) throws SQLException {
        String sql = "SELECT * FROM countries WHERE " + values + ";";

        Statement stm = startStatement();
        stm.execute(sql);
        ResultSet resultSet = stm.getResultSet();
        return map(resultSet);
    }

    /**
     * Specific function to countries used only for the ByCountryReport due to its very specific call.
     * @return
     * @throws SQLException
     */
    public ObservableList getApptsByCountry() throws SQLException {
        ObservableList<Appointments> countryAppts = FXCollections.observableArrayList();

        String query = "SELECT country.Country, appt.Type, COUNT(Appointment_ID) as Count FROM appointments as appt JOIN customers as cust ON appt.Customer_ID = cust.Customer_ID JOIN first_level_divisions as divs ON cust.Division_ID = divs.Division_ID JOIN countries as country ON divs.Country_ID = country.Country_ID GROUP BY Country, Type;";

        DBQuery.setPreparedStatement(query);
        PreparedStatement prpstm = DBQuery.getPreparedStatement();

        try{
            prpstm.execute();
            ResultSet rs = prpstm.getResultSet();
            while(rs.next()) {
                Appointments appt = new Appointments();

                appt.setCountry(rs.getString("Country"));
                appt.setType(rs.getString("Type"));
                appt.setCount(rs.getInt("Count"));

                countryAppts.add(appt);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return countryAppts;
    }

}
