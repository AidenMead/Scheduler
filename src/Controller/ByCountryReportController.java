package Controller;

import DAO.CountriesDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ByCountryReportController implements Initializable {
    @FXML public TableView countryTable;
    @FXML public TableColumn countryCol;
    @FXML public TableColumn typeCol;
    @FXML public TableColumn countCol;

    /**
     * Sets the table which displays Appointments based on what country the customer is in,
     * then grouped into types under the country.
     * @throws SQLException
     */
    public void setCountryTable() throws SQLException {
        CountriesDAOImpl countriesDAO = new CountriesDAOImpl();
        ObservableList apptList = FXCollections.observableArrayList(countriesDAO.getApptsByCountry());

        countryCol.setCellValueFactory(new PropertyValueFactory<>("Country"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("Count"));

        countryTable.getColumns().setAll(countryCol, typeCol, countCol);
        countryTable.setItems(apptList);

    }

    /**
     * Class specific initialize method which runs at the moment the scene initializes.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setCountryTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * Handles the button press event to take the user back to the main view.
     * @param actionEvent
     * @throws IOException
     */
    public void backHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/MainView.fxml"));
        Main.window.setTitle("Appointments");
        Main.window.setScene(new Scene(root, 1200, 800));
        Main.window.centerOnScreen();
        Main.window.show();
    }
}
