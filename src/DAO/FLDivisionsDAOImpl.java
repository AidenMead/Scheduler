package DAO;


import Model.FLDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FLDivisionsDAOImpl extends BaseDAO<FLDivisions>{

    /**
     * Division specific mapping to an observable list
     * @param resultSet
     * @return
     * @throws SQLException
     */
    @Override
    public ObservableList<FLDivisions> map(ResultSet resultSet) throws SQLException {
        ObservableList<FLDivisions> divisions = FXCollections.observableArrayList();

        while(resultSet.next()){
            FLDivisions division = new FLDivisions();

            division.setCountry_ID(resultSet.getInt("Country_ID"));
            division.setDivision(resultSet.getString("Division"));
            division.setDivision_ID(resultSet.getInt("Division_ID"));

            divisions.add(division);
        }

        return divisions;
    }

    /**
     * Divisions specific insert method - not used for this project.
     * @param newFLDivision
     * @return
     * @throws SQLException
     */
    @Override
    public boolean insert(FLDivisions newFLDivision) throws SQLException {
        //No need to add Divisions for this project
        return false;
    }

    /**
     * Divisions specific update method - not used for this project.
     * @param updatedFLDivision
     * @return
     */
    @Override
    public boolean update(FLDivisions updatedFLDivision) {
        //No need to update Divisions for this project
        return false;
    }

    /**
     * Divisions specific delete method - not used for this project.
     * @param divToDelete
     * @return
     */
    @Override
    public boolean delete(FLDivisions divToDelete) {
        //No need to delete Divisions for this project
        return false;
    }

    /**
     * Divisions specific selectOne method - not used for this project.
     * @param values
     * @return
     * @throws SQLException
     */
    @Override
    public List selectOne(String values) throws SQLException {
        return null;
    }
}
